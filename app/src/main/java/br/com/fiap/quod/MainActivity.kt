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
import androidx.activity.result.launch
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
import com.google.mlkit.vision.face.FaceContour
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceLandmark
import java.io.File
import java.io.FileOutputStream
import android.Manifest
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
//
//@Composable
//fun FaceComparisonScreen() {
//    val context = LocalContext.current
//    var userBitmap by remember { mutableStateOf<Bitmap?>(null) }
//    var documentBitmap by remember { mutableStateOf<Bitmap?>(null) }
//    var comparisonResult by remember { mutableStateOf<String?>(null) }
//
//    // Launcher for camera
//    val cameraLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.TakePicturePreview()
//    ) { bitmap ->
//        bitmap?.let {
//            if (userBitmap == null) {
//                userBitmap = it
//            } else {
//                documentBitmap = it
//                compareImages(userBitmap!!, documentBitmap!!, context) { result ->
//                    comparisonResult = result
//                }
//            }
//        }
//    }
//
//    // Launcher for gallery
//    val galleryLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri: Uri? ->
//        uri?.let {
//            val bitmap = context.getBitmapFromUri(it)
//            if (userBitmap == null) {
//                userBitmap = bitmap
//            } else {
//                documentBitmap = bitmap
//                compareImages(userBitmap!!, documentBitmap!!, context) { result ->
//                    comparisonResult = result
//                }
//            }
//        }
//    }
//
//    // Permission launcher
//    val permissionLauncher = rememberLauncherForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) { isGranted ->
//        if (isGranted) {
//            cameraLauncher.launch()
//        } else {
//            Toast.makeText(context, "Permissão necessária", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        // User Photo Section
//        Text("Foto do Usuário")
//        userBitmap?.let { bitmap ->
//            Image(
//                bitmap = bitmap.asImageBitmap(),
//                contentDescription = "Foto do Usuário",
//                modifier = Modifier.size(200.dp)
//            )
//        }
//        Button(onClick = {
//            if (userBitmap == null) {
//                // Solicitar permissão da câmera
//                permissionLauncher.launch(Manifest.permission.CAMERA)
//            } else {
//                // Abrir galeria para documento
//                galleryLauncher.launch("image/*")
//            }
//        }) {
//            Text(if (userBitmap == null) "Tirar Foto" else "Escolher Documento")
//        }
//
//        // Comparison Result
//        comparisonResult?.let { result ->
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(
//                text = result,
//                color = if (result.contains("Mesma pessoa")) MaterialTheme.colorScheme.primary
//                else MaterialTheme.colorScheme.error
//            )
//        }
//    }
//}
//
//// Função de extensão para converter URI para Bitmap
//fun Context.getBitmapFromUri(uri: Uri): Bitmap? {
//    return try {
//        val bitmap = android.provider.MediaStore.Images.Media.getBitmap(contentResolver, uri)
//        bitmap
//    } catch (e: Exception) {
//        null
//    }
//}
//
//// Função para comparar imagens
//fun compareImages(
//    userBitmap: Bitmap,
//    documentBitmap: Bitmap,
//    context: Context,
//    onResult: (String) -> Unit
//) {
//    val options = FaceDetectorOptions.Builder()
//        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
//        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
//        .build()
//
//    val detector = FaceDetection.getClient(options)
//
//    // Preparar imagens para detecção
//    val userImage = InputImage.fromBitmap(userBitmap, 0)
//    val documentImage = InputImage.fromBitmap(documentBitmap, 0)
//
//    // Processar detecção de faces
//    detector.process(userImage)
//        .addOnSuccessListener { userFaces ->
//            detector.process(documentImage)
//                .addOnSuccessListener { documentFaces ->
//                    if (userFaces.isNotEmpty() && documentFaces.isNotEmpty()) {
//                        val userFace = userFaces[0]
//                        val documentFace = documentFaces[0]
//
//                        // Comparação mais sofisticada
//                        val similarityScore = calculateFaceSimilarity(userFace, documentFace)
//
//                        val result = if (similarityScore > 0.7) {
//                            "As imagens são da mesma pessoa (Similaridade: ${(similarityScore * 100).toInt()}%)"
//                        } else {
//                            "As imagens NÃO são da mesma pessoa (Similaridade: ${(similarityScore * 100).toInt()}%)"
//                        }
//
//                        onResult(result)
//                    } else {
//                        onResult("Nenhum rosto detectado em uma das imagens")
//                    }
//                }
//                .addOnFailureListener {
//                    onResult("Erro na detecção de faces do documento")
//                }
//        }
//        .addOnFailureListener {
//            onResult("Erro na detecção de faces do usuário")
//        }
//}
//
//// Função para calcular similaridade de faces (simplificada)
//fun calculateFaceSimilarity(face1: com.google.mlkit.vision.face.Face, face2: com.google.mlkit.vision.face.Face): Float {
//    // Comparação baseada em proporções fciais
//    val sizeSimilarity = 1 - (Math.abs(face1.boundingBox.width() - face2.boundingBox.width()) /
//            face1.boundingBox.width().toFloat())
//
//    val positionSimilarity = 1 - (Math.abs(face1.boundingBox.centerX() - face2.boundingBox.centerX()) /
//            face1.boundingBox.width().toFloat())
//
//    // Combinar métricas (você pode refinar este método)
//    return (sizeSimilarity + positionSimilarity) / 2
//}

class MainActivity : ComponentActivity() { //AppCompatActivity() {
        private val CAMERA_PERMISSION_REQUEST_CODE = 123
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
        val context = LocalContext.current
        val activity = LocalContext.current as? Activity // Get Activity instance
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicturePreview(),
            onResult = { bitmap: Bitmap? ->
                if (bitmap != null) {
                    val file = File.createTempFile("image", ".jpg", context.cacheDir)
                    val outputStream = FileOutputStream(file)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    outputStream.close()
                    imageUri = Uri.fromFile(file)
                }
            }
        )

        Column {
            if (imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = "Captured Image",
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Button(onClick = {
                if (activity != null) {
                    if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                        launcher.launch()
                    } else {
                        ActivityCompat.requestPermissions(
                            activity,
                            arrayOf(Manifest.permission.CAMERA),
                            CAMERA_PERMISSION_REQUEST_CODE
                        )
                    }
                }
            }) {
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
                val image = InputImage.fromFilePath(context, imageUri!!)
                compareFaces(image) { result ->
                    isSamePerson = result
                }
            }
        }
    }

    private fun compareFaces(image: InputImage, callback: (Boolean) -> Unit) {
        val detector = FaceDetection.getClient() // Use as opções desejadas

        detector.process(image)
            .addOnSuccessListener { faces ->
                if (faces.size >= 2) {
                    // Compare as faces usando seus recursos (bounding box, landmarks, etc.)
                    // e determine se são da mesma pessoa
                    val isSame = areFacesSimilar(faces[0], faces[1])
                    callback(isSame)
                } else {
                    // Trate o caso em que menos de 2 faces foram detectadas
                    callback(false) // Ou lance uma exceção
                }
            }
            .addOnFailureListener { e ->
                // Trate erros de detecção de rosto
                callback(false) // Ou lance uma exceção
            }
    }

    private fun areFacesSimilar(face1: Face, face2: Face): Boolean {
        // Implemente sua lógica de comparação de faces aqui
        // Compare características como distância entre landmarks, ângulos de rotação, etc.
        // Retorne true se as faces forem consideradas semelhantes, false caso contrário

        // Exemplo simples usando a distância entre os olhos:
        val leftEye1 = face1.getLandmark(FaceLandmark.LEFT_EYE)?.position
        val rightEye1 = face1.getLandmark(FaceLandmark.RIGHT_EYE)?.position
        val leftEye2 = face2.getLandmark(FaceLandmark.LEFT_EYE)?.position
        val rightEye2 = face2.getLandmark(FaceLandmark.RIGHT_EYE)?.position

        if (leftEye1 != null && rightEye1 != null && leftEye2 != null && rightEye2 != null) {
            val distance1 = distance(leftEye1, rightEye1)
            val distance2 = distance(leftEye2, rightEye2)
            val threshold = 0.1f // Defina um limite de similaridade

            return abs(distance1 - distance2) < threshold
        }

        return false
    }

    private fun distance(point1: PointF, point2: PointF): Float {
        return sqrt((point1.x - point2.x).pow(2) + (point1.y - point2.y).pow(2))
    }

    private fun detectFaces(image: InputImage) {
        // [START set_detector_options]
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .setMinFaceSize(0.15f)
            .enableTracking()
            .build()
        // [END set_detector_options]

        // [START get_detector]
        val detector = FaceDetection.getClient(options)
        // Or, to use the default option:
        // val detector = FaceDetection.getClient();
        // [END get_detector]

        // [START run_detector]
        val result = detector.process(image)
            .addOnSuccessListener { faces ->
                // Task completed successfully
                // [START_EXCLUDE]
                // [START get_face_info]
                for (face in faces) {
                    val bounds = face.boundingBox
                    val rotY = face.headEulerAngleY // Head is rotated to the right rotY degrees
                    val rotZ = face.headEulerAngleZ // Head is tilted sideways rotZ degrees

                    // If landmark detection was enabled (mouth, ears, eyes, cheeks, and
                    // nose available):
                    val leftEar = face.getLandmark(FaceLandmark.LEFT_EAR)
                    leftEar?.let {
                        val leftEarPos = leftEar.position
                    }

                    // If classification was enabled:
                    if (face.smilingProbability != null) {
                        val smileProb = face.smilingProbability
                    }
                    if (face.rightEyeOpenProbability != null) {
                        val rightEyeOpenProb = face.rightEyeOpenProbability
                    }

                    // If face tracking was enabled:
                    if (face.trackingId != null) {
                        val id = face.trackingId
                    }
                }
                // [END get_face_info]
                // [END_EXCLUDE]
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                // ...
            }
        // [END run_detector]
    }

    private fun faceOptionsExamples() {
        // [START mlkit_face_options_examples]
        // High-accuracy landmark detection and face classification
        val highAccuracyOpts = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()

        // Real-time contour detection
        val realTimeOpts = FaceDetectorOptions.Builder()
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .build()
        // [END mlkit_face_options_examples]
    }

    private fun processFaceList(faces: List<Face>) {
        // [START mlkit_face_list]
        for (face in faces) {
            val bounds = face.boundingBox
            val rotY = face.headEulerAngleY // Head is rotated to the right rotY degrees
            val rotZ = face.headEulerAngleZ // Head is tilted sideways rotZ degrees

            // If landmark detection was enabled (mouth, ears, eyes, cheeks, and
            // nose available):
            val leftEar = face.getLandmark(FaceLandmark.LEFT_EAR)
            leftEar?.let {
                val leftEarPos = leftEar.position
            }

            // If contour detection was enabled:
            val leftEyeContour = face.getContour(FaceContour.LEFT_EYE)?.points
            val upperLipBottomContour = face.getContour(FaceContour.UPPER_LIP_BOTTOM)?.points

            // If classification was enabled:
            if (face.smilingProbability != null) {
                val smileProb = face.smilingProbability
            }
            if (face.rightEyeOpenProbability != null) {
                val rightEyeOpenProb = face.rightEyeOpenProbability
            }

            // If face tracking was enabled:
            if (face.trackingId != null) {
                val id = face.trackingId
            }
        }
        // [END mlkit_face_list]
    }
}