package br.com.fiap.quod.documentoscopia

//import com.google.mlkit.vision.face.FaceDetection
//import com.google.mlkit.vision.face.FaceDetector
//import com.google.mlkit.vision.common.InputImage
//
//fun compareFaces(capturedImageUri: Uri, referenceImageUri: Uri, context: Context) {
//    val faceDetector: FaceDetector = FaceDetection.getClient()
//
//    val capturedBitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, capturedImageUri)
//    val referenceBitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, referenceImageUri)
//
//    val capturedImage = InputImage.fromBitmap(capturedBitmap, 0)
//    val referenceImage = InputImage.fromBitmap(referenceBitmap, 0)
//
//    // Detect faces in the captured image
//    faceDetector.process(capturedImage)
//        .addOnSuccessListener { facesCaptured ->
//            faceDetector.process(referenceImage)
//                .addOnSuccessListener { facesReference ->
//                    if (facesCaptured.isNotEmpty() && facesReference.isNotEmpty()) {
//                        // A face was detected in both images
//                        // Implement further comparison logic here (e.g., comparing face features)
//                        val isSamePerson = compareFacesData(facesCaptured, facesReference)
//                        if (isSamePerson) {
//                            Log.d("FaceComparison", "As imagens são de uma mesma pessoa.")
//                        } else {
//                            Log.d("FaceComparison", "As imagens são de pessoas diferentes.")
//                        }
//                    }
//                }
//        }
//}

//fun compareFacesData(facesCaptured: List<Face>, facesReference: List<Face>): Boolean {
//    // Compare the features of the detected faces here.
//    // This is a basic check for the presence of faces, further logic can be added for more accurate comparison.
//    return facesCaptured.size == facesReference.size
//}
