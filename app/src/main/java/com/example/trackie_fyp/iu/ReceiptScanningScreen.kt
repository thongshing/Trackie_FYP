package com.example.trackie_fyp.iu


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.trackie_fyp.models.ReceiptViewModel





@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptScanningScreen(navController: NavHostController, viewModel: ReceiptViewModel = viewModel()) {
    DisposableEffect(Unit) {
        onDispose {
            viewModel.resetTotalAmount() // Reset total amount and error when leaving the screen
        }
    }

    Scaffold(
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val cameraKey = remember { mutableStateOf(0) }

                // Reset the cameraKey when the screen is first composed
                LaunchedEffect(Unit) {
                    cameraKey.value++
                }

                CameraCapture(
                    key = cameraKey.value,
                    onImageCaptured = { bitmap ->
                        viewModel.processImage(bitmap)
                    },
                    onError = { exception ->
                        // Handle error
                        exception.printStackTrace()
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                val ocrResult by viewModel.ocrResult.observeAsState(initial = emptyList())
                val error by viewModel.error.observeAsState()

                if (error != null) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = error!!)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            cameraKey.value++
                            viewModel.resetTotalAmount()
                        }) {
                            Text("Retry")
                        }
                    }
                } else {
                    LazyColumn {
                        items(ocrResult) { item ->
                            Text(text = item)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    val totalAmount by viewModel.totalAmount.observeAsState()
                    val receiptDate by viewModel.receiptDate.observeAsState()
                    totalAmount?.let {
                        Text("Total Amount: $$it", modifier = Modifier.padding(8.dp))
                        LaunchedEffect(it) {
                            val date = receiptDate ?: "unknown" // Provide a default value for the date
                            navController.navigate("totalAmount/$it/$date")
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ReceiptScanningScreenPreview() {
    val navController = rememberNavController()
    ReceiptScanningScreen(navController = navController)
}
