package br.com.fiap.quod.documentoscopia

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.PointF
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceLandmark
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

private const val CAMERA_PERMISSION_REQUEST_CODE = 123
private const val SIMILARITY_THRESHOLD = 0.85f

@Composable
fun FaceComparisonComponent() {
    val coroutineScope = rememberCoroutineScope()

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isSamePerson by remember { mutableStateOf<Boolean?>(null) }
    var processingStatus by remember { mutableStateOf("") }
    var faceDetected by remember { mutableIntStateOf(0) }

    val context = LocalContext.current
    val activity = LocalContext.current as? Activity

    val photoFile = remember {
        File.createTempFile(
            "IMG_",
            ".jpg",
            context.cacheDir
        ).apply {
            createNewFile()
            deleteOnExit()
        }
    }

    val photoUri = remember {
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            photoFile
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                coroutineScope.launch {
                    processingStatus = "Otimizando imagem..."
                    try {
                        withContext(Dispatchers.IO) {
                            val optimizedImageFile = optimizeImage(photoFile, context)
                            withContext(Dispatchers.Main) {
                                imageUri = Uri.fromFile(optimizedImageFile)
                                processingStatus = ""
                            }
                        }
                    } catch (e: Exception) {
                        processingStatus = "Erro ao otimizar imagem: ${e.message}"
                    }
                }
            }
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .wrapContentSize(Alignment.Center),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .wrapContentSize(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(context)
                            .data(imageUri)
                            .size(Size.ORIGINAL)
                            .build()
                    ),
                    contentDescription = "Captured Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            Button(
                onClick = {
                    if (activity != null) {
                        when {
                            ContextCompat.checkSelfPermission(
                                activity,
                                Manifest.permission.CAMERA
                            ) == PackageManager.PERMISSION_GRANTED -> {
                                launcher.launch(photoUri)
                            }
                            else -> {
                                ActivityCompat.requestPermissions(
                                    activity,
                                    arrayOf(Manifest.permission.CAMERA),
                                    CAMERA_PERMISSION_REQUEST_CODE
                                )
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Tirar Foto")
            }

            if (isSamePerson != null) {
                Text(
                    text = if (isSamePerson == true) "Mesma Pessoa" else "Pessoas Diferentes",
                    color = if (isSamePerson == true) Color.Green else Color.Red
                )
            }
        }
    }

    LaunchedEffect(imageUri) {
        if (imageUri != null) {
            processingStatus = "Processando imagem..."
            try {
                val image = InputImage.fromFilePath(context, imageUri!!)
                compareFaces(image) { result, numFaces ->
                    isSamePerson = result
                    faceDetected = numFaces
                    processingStatus = ""
                }
            } catch (e: Exception) {
                processingStatus = "Erro ao processar imagem: ${e.message}"
            }
        }
    }
}

private suspend fun optimizeImage(originalFile: File, context: Context): File {
    return withContext(Dispatchers.IO) {
        val targetWidth = 2048  // Largura máxima desejada
        val targetHeight = 2048 // Altura máxima desejada

        // Carregar as dimensões da imagem original
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeFile(originalFile.absolutePath, options)

        // Calcular o fator de escala
        var scale = 1
        while (options.outWidth / scale > targetWidth ||
            options.outHeight / scale > targetHeight) {
            scale *= 2
        }

        // Carregar a imagem com o fator de escala calculado
        val scaledOptions = BitmapFactory.Options().apply {
            inSampleSize = scale
            inJustDecodeBounds = false
        }

        val bitmap = BitmapFactory.decodeFile(originalFile.absolutePath, scaledOptions)

        // Rotacionar a imagem se necessário
        val rotatedBitmap = bitmap?.let { rotateBitmapIfNeeded(it, originalFile.absolutePath) }

        // Criar novo arquivo para a imagem otimizada
        val optimizedFile = File.createTempFile("optimized_", ".jpg", context.cacheDir)

        // Salvar a imagem otimizada
        FileOutputStream(optimizedFile).use { out ->
            rotatedBitmap?.compress(Bitmap.CompressFormat.JPEG, 95, out)
        }

        // Limpar recursos
        bitmap?.recycle()
        rotatedBitmap?.recycle()

        optimizedFile
    }
}

private fun rotateBitmapIfNeeded(bitmap: Bitmap, path: String): Bitmap {
    val ei = ExifInterface(path)
    return when (ei.getAttributeInt(
        ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_NORMAL
    )) {
        ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90f)
        ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180f)
        ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270f)
        else -> bitmap
    }
}

private fun rotateImage(source: Bitmap, angle: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(
        source, 0, 0, source.width, source.height,
        matrix, true
    )
}

private fun compareFaces(image: InputImage, callback: (Boolean, Int) -> Unit) {
    val options = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
        .setMinFaceSize(0.15f)
        .enableTracking()
        .build()

    val detector = FaceDetection.getClient(options)

    detector.process(image)
        .addOnSuccessListener { faces ->
            if (faces.size >= 2) {
                val isSame = areFacesSimilar(faces[0], faces[1])
                callback(isSame, faces.size)
            } else {
                callback(false, faces.size)
            }
        }
        .addOnFailureListener { e ->
            Log.e("FaceComparison", "Error detecting faces", e)
            callback(false, 0)
        }
}

private fun areFacesSimilar(face1: Face, face2: Face): Boolean {
    // Lista de características para comparar
    var similarityScore = 0f
    var totalFeatures = 0f

    // 1. Comparar distância entre olhos
    val leftEye1 = face1.getLandmark(FaceLandmark.LEFT_EYE)?.position
    val rightEye1 = face1.getLandmark(FaceLandmark.RIGHT_EYE)?.position
    val leftEye2 = face2.getLandmark(FaceLandmark.LEFT_EYE)?.position
    val rightEye2 = face2.getLandmark(FaceLandmark.RIGHT_EYE)?.position

    if (leftEye1 != null && rightEye1 != null && leftEye2 != null && rightEye2 != null) {
        val eyeDistance1 = distance(leftEye1, rightEye1)
        val eyeDistance2 = distance(leftEye2, rightEye2)
        val eyeRatio = minOf(eyeDistance1, eyeDistance2) / maxOf(eyeDistance1, eyeDistance2)
        similarityScore += eyeRatio
        totalFeatures++
    }

    // 2. Comparar ângulos da cabeça
    val rotationScore = 1f - (
            abs(face1.headEulerAngleY - face2.headEulerAngleY) / 90f +
                    abs(face1.headEulerAngleZ - face2.headEulerAngleZ) / 90f
            ) / 2f
    similarityScore += rotationScore
    totalFeatures++

    // 3. Comparar distância entre olhos e boca
    val mouth1 = face1.getLandmark(FaceLandmark.MOUTH_BOTTOM)?.position
    val mouth2 = face2.getLandmark(FaceLandmark.MOUTH_BOTTOM)?.position

    if (mouth1 != null && mouth2 != null && leftEye1 != null && leftEye2 != null) {
        val mouthEyeDistance1 = distance(leftEye1, mouth1)
        val mouthEyeDistance2 = distance(leftEye2, mouth2)
        val mouthRatio = minOf(mouthEyeDistance1, mouthEyeDistance2) /
                maxOf(mouthEyeDistance1, mouthEyeDistance2)
        similarityScore += mouthRatio
        totalFeatures++
    }

    // 4. Comparar características de classificação
    face1.smilingProbability?.let { smile1 ->
        face2.smilingProbability?.let { smile2 ->
            val smileScore = 1f - abs(smile1 - smile2)
            similarityScore += smileScore
            totalFeatures++
        }
    }

    face1.rightEyeOpenProbability?.let { eye1 ->
        face2.rightEyeOpenProbability?.let { eye2 ->
            val eyeScore = 1f - abs(eye1 - eye2)
            similarityScore += eyeScore
            totalFeatures++
        }
    }

    // Calcular pontuação final
    val finalScore = if (totalFeatures > 0) similarityScore / totalFeatures else 0f

    Log.d("FaceComparison", "Similarity Score: $finalScore")
    return finalScore >= SIMILARITY_THRESHOLD
}

private fun distance(point1: PointF, point2: PointF): Float {
    return sqrt((point1.x - point2.x).pow(2) + (point1.y - point2.y).pow(2))
}