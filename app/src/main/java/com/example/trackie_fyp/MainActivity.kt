package com.example.trackie_fyp

import MainScreen
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.trackie_fyp.iu.LoginScreen
import com.example.trackie_fyp.iu.SignUpScreen
import com.example.trackie_fyp.models.UserViewModel
import com.example.trackie_fyp.ui.theme.AppThemeSwitcher
import com.example.trackie_fyp.ui.theme.Trackie_FYPTheme
import com.github.mikephil.charting.utils.Utils

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize MPAndroidChart Utils
        Utils.init(this)
        // Initialize AppThemeSwitcher
        AppThemeSwitcher.init(this)

        setContent {
            val userViewModel: UserViewModel = viewModel()
            val userId by userViewModel.userId.observeAsState(-1)
            val isDarkMode by AppThemeSwitcher.isDarkMode

            Log.d("MainActivity", "Retrieved userId: $userId from ViewModel")
            val navController = rememberNavController()

            Trackie_FYPTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (userId != -1) {
                        LaunchedEffect(userId) {
                            Log.d("MainActivity", "Launching effect with userId: $userId")
                            navController.navigate("main/$userId") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    } else {
                        LaunchedEffect(Unit) {
                            navController.navigate("login")
                        }
                    }

                    NavHost(
                        navController = navController,
                        startDestination = "splash" // Temporary start destination
                    ) {
                        composable("splash") { /* Show splash screen or loading screen */ }
                        composable("login") {
                            LoginScreen(navController = navController, onLoginSuccess = { newUserId ->
                                userViewModel.setUserId(newUserId)
                                Log.d("MainActivity", "Login successful, navigating to main with userId: $newUserId")
                                navController.navigate("main/$newUserId") {
                                    popUpTo("login") { inclusive = true }
                                }
                            })
                        }
                        composable("signup") { SignUpScreen(navController) }
                        composable(
                            "main/{userId}",
                            arguments = listOf(navArgument("userId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val userId = backStackEntry.arguments?.getInt("userId") ?: -1
                            Log.d("MainActivity", "Navigating to MainScreen with userId: $userId")
                            MainScreen(userId = userId)
                        }
                    }
                }
            }
        }
    }
}