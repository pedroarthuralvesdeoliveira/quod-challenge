package br.com.fiap.quod.documentoscopia

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.io.File
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Composable
fun CameraScreen() {
    val context = LocalContext.current
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
    val imageCapture = remember { ImageCapture.Builder().build() }

    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

    var imageUri = remember { mutableStateOf<Uri?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = {
            takePhoto(context, imageCapture, cameraExecutor) { uri ->
                imageUri = uri as MutableState<Uri?>
            }
        }) {
            Text("Capturar Foto")
        }

        imageUri.let {
            //Image(painter = rememberImagePainter(it), contentDescription = "Foto do Documento")
        }
    }
}

fun takePhoto(
    context: Context,
    imageCapture: ImageCapture,
    cameraExecutor: Executor,
    onImageCaptured: (Uri) -> Unit
) {
    val outputOptions = ImageCapture.OutputFileOptions.Builder(File(context.filesDir, "photo.jpg")).build()
    imageCapture.takePicture(
        outputOptions,
        cameraExecutor,
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val savedUri = outputFileResults.savedUri
                onImageCaptured(savedUri)
            }

            private fun onImageCaptured(uri: Uri?) {

            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("CameraX", "Erro ao capturar imagem", exception)
            }
        }
    )
}
