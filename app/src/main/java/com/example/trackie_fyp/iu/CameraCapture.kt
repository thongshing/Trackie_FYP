package com.example.trackie_fyp.iu

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import java.io.File


@Composable
fun CameraCapture(
    key: Int,
    onImageCaptured: (Bitmap) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }

    val previewView = remember { PreviewView(context) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                processCapturedImage(context, it, onImageCaptured)
            }
        }
    )

    LaunchedEffect(cameraProviderFuture, key) {
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

        imageCapture = ImageCapture.Builder().build()

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("CameraCapture", "Use case binding failed", e)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            Log.d("CameraCapture", "Capture button clicked")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, "IMG_${System.currentTimeMillis()}.jpg")
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/CameraX-Images")
                }
                val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
                val outputOptions = ImageCapture.OutputFileOptions.Builder(
                    context.contentResolver,
                    collection,
                    contentValues
                ).build()
                takePicture(imageCapture, outputOptions, context, onImageCaptured, onError)
            } else {
                val file = createTempFile(context)
                val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()
                takePicture(imageCapture, outputOptions, context, onImageCaptured, onError)
            }
        }) {
            Text("Capture")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }) {
            Text("Select Image")
        }
    }
}

private fun takePicture(
    imageCapture: ImageCapture?,
    outputOptions: ImageCapture.OutputFileOptions,
    context: Context,
    onImageCaptured: (Bitmap) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    imageCapture?.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                val uri = output.savedUri
                Log.d("CameraCapture", "Image saved at: $uri")
                uri?.let {
                    try {
                        processCapturedImage(context, it, onImageCaptured)
                    } catch (e: Exception) {
                        Log.e("CameraCapture", "Error processing image", e)
                    }
                }
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("CameraCapture", "Image capture failed", exception)
                onError(exception)
            }
        }
    )
}

private fun createTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(null)
    return File.createTempFile(
        "JPEG_${System.currentTimeMillis()}_",
        ".jpg",
        storageDir
    )
}

private fun processCapturedImage(context: Context, uri: Uri, onImageCaptured: (Bitmap) -> Unit) {
    val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
    onImageCaptured(bitmap)
}




