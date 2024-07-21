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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.trackie_fyp.iu.LoginScreen
import com.example.trackie_fyp.iu.SignUpScreen
import com.example.trackie_fyp.ui.theme.AppThemeSwitcher
import com.example.trackie_fyp.ui.theme.Trackie_FYPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Clear SharedPreferences for testing
        val sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
        Log.d("MainActivity", "Cleared SharedPreferences manually for testing.")

        setContent {
            val isDarkMode by AppThemeSwitcher.isDarkMode
            Trackie_FYPTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val userId = sharedPreferences.getInt("USER_ID", -1)
                    Log.d("MainActivity", "Retrieved userId: $userId from SharedPreferences")
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = if (userId != -1) "main/$userId" else "login"
                    ) {
                        composable("login") {
                            LoginScreen(navController = navController, onLoginSuccess = { newUserId ->
                                sharedPreferences.edit().putInt("USER_ID", newUserId).apply()
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