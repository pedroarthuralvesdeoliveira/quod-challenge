package br.com.fiap.quod

import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.PointF
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceLandmark
import java.io.File
import java.io.FileOutputStream
import android.Manifest
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import coil.request.ImageRequest
import coil.size.Size
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

//import android.app.Activity
//import android.content.ActivityNotFoundException
//import android.content.Intent
//import android.os.Bundle
//import android.provider.MediaStore
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.activity.result.ActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.camera.core.ImageCapture
//import androidx.compose.material3.Surface
//import br.com.fiap.quod.documentoscopia.ValidatePhotoIDWithSelfie
//import br.com.fiap.quod.documentoscopia.ValidatePhotoIDWithSelfieViewModel
//import br.com.fiap.quod.ui.theme.QuodTheme
//import java.util.concurrent.ExecutorService
//
//class MainActivity : ComponentActivity() {
//
//    private var imageCapture: ImageCapture? = null
//    private lateinit var cameraExecutor: ExecutorService
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            QuodTheme {
//                Surface {
////                    CheckSIMSwap(checkSIMSwapViewModel = CheckSIMSwapViewModel())
////                    AntiFraude(antiFraudScreenViewModel = AntiFraudScreenViewModel())
////                    Auth(authScreenViewModel = AuthScreenViewModel())
//                    ValidatePhotoIDWithSelfie(validatePhotoIDWithSelfieViewModel = ValidatePhotoIDWithSelfieViewModel())
//                }
//            }
//        }
//    }
//}

//import android.Manifest
//import android.content.Context
//import android.graphics.Bitmap
//import android.net.Uri
//import android.os.Bundle
//import android.widget.Toast
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.compose.setContent
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.Button
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.asImageBitmap
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import com.google.mlkit.vision.common.InputImage import com.google.mlkit.vision.face.FaceDetection
//import com.google.mlkit.vision.interfaces.Detector
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MaterialTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    FaceComparisonScreen()
//                }
//            }
//        }
//    }
//}

//class MainActivity : ComponentActivity() { //AppCompatActivity() {
//        private val CAMERA_PERMISSION_REQUEST_CODE = 123
//        override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MaterialTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    FaceComparisonComponent()
//                }
//            }
//        }
//    }
//
//    @Composable
//    fun FaceComparisonComponent() {
//        var imageUri by remember { mutableStateOf<Uri?>(null) }
//        var isSamePerson by remember { mutableStateOf<Boolean?>(null) }
//        val context = LocalContext.current
//        val activity = LocalContext.current as? Activity // Get Activity instance
//        val launcher = rememberLauncherForActivityResult(
//            contract = ActivityResultContracts.TakePicturePreview(),
//            onResult = { bitmap: Bitmap? ->
//                if (bitmap != null) {
//                    val file = File.createTempFile("image", ".jpg", context.cacheDir)
//                    val outputStream = FileOutputStream(file)
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
//                    outputStream.close()
//                    imageUri = Uri.fromFile(file)
//                }
//            }
//        )
//
//        Column {
//            if (imageUri != null) {
//                Image(
//                    painter = rememberAsyncImagePainter(imageUri),
//                    contentDescription = "Captured Image",
//                    modifier = Modifier.fillMaxWidth()
//                )
//            }
//
//            Button(onClick = {
//                if (activity != null) {
//                    if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
//                        == PackageManager.PERMISSION_GRANTED) {
//                        launcher.launch()
//                    } else {
//                        ActivityCompat.requestPermissions(
//                            activity,
//                            arrayOf(Manifest.permission.CAMERA),
//                            CAMERA_PERMISSION_REQUEST_CODE
//                        )
//                    }
//                }
//            }) {
//                Text("Tirar Foto")
//            }
//
//            if (isSamePerson != null) {
//                Text(
//                    text = if (isSamePerson == true) "Mesma Pessoa" else "Pessoas Diferentes",
//                    color = if (isSamePerson == true) Color.Green else Color.Red
//                )
//            }
//        }
//
//        LaunchedEffect(imageUri) {
//            if (imageUri != null) {
//                val image = InputImage.fromFilePath(context, imageUri!!)
//                detectFaces(image)
//                    .addOnSuccessListener { faces ->
//                        processFaceList(faces)
//                    }
//                    .addOnFailureListener { e ->
//                        // Trate erros de detecção de rosto
//                        // ...
//                    }
////                compareFaces(image) { result ->
////                    isSamePerson = result
////                }
//            }
//        }
//    }
//
//    private fun compareFaces(image: InputImage, callback: (Boolean) -> Unit) {
//        val detector = FaceDetection.getClient() // Use as opções desejadas
//
//        detector.process(image)
//            .addOnSuccessListener { faces ->
//                if (faces.size >= 2) {
//                    // Compare as faces usando seus recursos (bounding box, landmarks, etc.)
//                    // e determine se são da mesma pessoa
//                    val isSame = areFacesSimilar(faces[0], faces[1])
//                    callback(isSame)
//                } else {
//                    // Trate o caso em que menos de 2 faces foram detectadas
//                    callback(false) // Ou lance uma exceção
//                }
//            }
//            .addOnFailureListener { e ->
//                // Trate erros de detecção de rosto
//                callback(false) // Ou lance uma exceção
//            }
//    }
//
//    private fun areFacesSimilar(face1: Face, face2: Face): Boolean {
//        // Implemente sua lógica de comparação de faces aqui
//        // Compare características como distância entre landmarks, ângulos de rotação, etc.
//        // Retorne true se as faces forem consideradas semelhantes, false caso contrário
//
//        // Exemplo simples usando a distância entre os olhos:
//        val leftEye1 = face1.getLandmark(FaceLandmark.LEFT_EYE)?.position
//        val rightEye1 = face1.getLandmark(FaceLandmark.RIGHT_EYE)?.position
//        val leftEye2 = face2.getLandmark(FaceLandmark.LEFT_EYE)?.position
//        val rightEye2 = face2.getLandmark(FaceLandmark.RIGHT_EYE)?.position
//
//        if (leftEye1 != null && rightEye1 != null && leftEye2 != null && rightEye2 != null) {
//            val distance1 = distance(leftEye1, rightEye1)
//            val distance2 = distance(leftEye2, rightEye2)
//            val threshold = 0.1f // Defina um limite de similaridade
//
//            return abs(distance1 - distance2) < threshold
//        }
//
//        return false
//    }
//
//    private fun distance(point1: PointF, point2: PointF): Float {
//        return sqrt((point1.x - point2.x).pow(2) + (point1.y - point2.y).pow(2))
//    }
//
//    private fun detectFaces(image: InputImage) : Task<List<Face>> {
//        // [START set_detector_options]
//        val options = FaceDetectorOptions.Builder()
//            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
//            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
//            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
//            .setMinFaceSize(0.15f)
//            .enableTracking()
//            .build()
//        // [END set_detector_options]
//
//        // [START get_detector]
//        val detector = FaceDetection.getClient(options)
//        // Or, to use the default option:
//        // val detector = FaceDetection.getClient();
//        // [END get_detector]
//
//        // [START run_detector]
//        return detector.process(image)
//            .addOnSuccessListener { faces ->
//                // Task completed successfully
//                // [START_EXCLUDE]
//                // [START get_face_info]
//                for (face in faces) {
//                    val bounds = face.boundingBox
//                    val rotY = face.headEulerAngleY // Head is rotated to the right rotY degrees
//                    val rotZ = face.headEulerAngleZ // Head is tilted sideways rotZ degrees
//
//                    // If landmark detection was enabled (mouth, ears, eyes, cheeks, and
//                    // nose available):
//                    val leftEar = face.getLandmark(FaceLandmark.LEFT_EAR)
//                    leftEar?.let {
//                        val leftEarPos = leftEar.position
//                    }
//
//                    // If classification was enabled:
//                    if (face.smilingProbability != null) {
//                        val smileProb = face.smilingProbability
//                    }
//                    if (face.rightEyeOpenProbability != null) {
//                        val rightEyeOpenProb = face.rightEyeOpenProbability
//                    }
//
//                    // If face tracking was enabled:
//                    if (face.trackingId != null) {
//                        val id = face.trackingId
//                    }
//                }
//                // [END get_face_info]
//                // [END_EXCLUDE]
//            }
//            .addOnFailureListener { e ->
//                // Task failed with an exception
//                // ...
//            }
//        // [END run_detector]
//    }
//
//    private fun faceOptionsExamples() : FaceDetectorOptions {
//        // [START mlkit_face_options_examples]
//        // High-accuracy landmark detection and face classification
//        val highAccuracyOpts = FaceDetectorOptions.Builder()
//            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
//            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
//            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
//            .build()
//
//        // Real-time contour detection
//        val realTimeOpts = FaceDetectorOptions.Builder()
//            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
//            .build()
//
//        return highAccuracyOpts
//        // [END mlkit_face_options_examples]
//    }
//
//    private fun processFaceList(faces: List<Face>) {
//        // [START mlkit_face_list]
//        for (face in faces) {
//            val bounds = face.boundingBox
//            val rotY = face.headEulerAngleY // Head is rotated to the right rotY degrees
//            val rotZ = face.headEulerAngleZ // Head is tilted sideways rotZ degrees
//
//            // If landmark detection was enabled (mouth, ears, eyes, cheeks, and
//            // nose available):
//            val leftEar = face.getLandmark(FaceLandmark.LEFT_EAR)
//            leftEar?.let {
//                val leftEarPos = leftEar.position
//            }
//
//            // If contour detection was enabled:
//            val leftEyeContour = face.getContour(FaceContour.LEFT_EYE)?.points
//            val upperLipBottomContour = face.getContour(FaceContour.UPPER_LIP_BOTTOM)?.points
//
//            // If classification was enabled:
//            if (face.smilingProbability != null) {
//                val smileProb = face.smilingProbability
//            }
//            if (face.rightEyeOpenProbability != null) {
//                val rightEyeOpenProb = face.rightEyeOpenProbability
//            }
//
//            // If face tracking was enabled:
//            if (face.trackingId != null) {
//                val id = face.trackingId
//            }
//        }
//        // [END mlkit_face_list]
//    }
//}

class MainActivity : ComponentActivity() {
    private val CAMERA_PERMISSION_REQUEST_CODE = 123
    private val SIMILARITY_THRESHOLD = 0.85f // Threshold for face similarity (85%)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FaceComparisonComponent()
                }
            }
        }
    }

    @Composable
    fun FaceComparisonComponent() {
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
                    lifecycleScope.launch {
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
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
                                    this@MainActivity.CAMERA_PERMISSION_REQUEST_CODE
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
}