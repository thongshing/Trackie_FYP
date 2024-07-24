package com.example.trackie_fyp.iu

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.trackie_fyp.models.LoginViewModel
import com.example.trackie_fyp.ui.theme.Trackie_FYPTheme

@Composable
fun LoginScreen(navController: NavHostController, onLoginSuccess: (Int) -> Unit, viewModel: LoginViewModel = viewModel()) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Login", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                loginError = null // Clear error when the user starts typing
            },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            isError = loginError != null
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                loginError = null // Clear error when the user starts typing
            },
            label = { Text("Password") },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (showPassword) {
                    Icons.Default.Visibility
                } else {
                    Icons.Default.VisibilityOff
                }
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = loginError != null
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (loginError != null) {
            Text(
                text = loginError ?: "",
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        Button(
            onClick = {
                viewModel.login(username, password) { userId, error ->
                    if (userId != -1) {
                        Log.d("LoginScreen", "Login successful, userId: $userId")
                        onLoginSuccess(userId)
                    } else {
                        Log.d("LoginScreen", "Login failed: $error")
                        loginError = error ?: "Login failed. Please try again."
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Sign Up",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { navController.navigate("signup") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Trackie_FYPTheme {
        LoginScreen(navController = rememberNavController(), onLoginSuccess = {})
    }
}