package com.example.trackie_fyp

import MainScreen
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.trackie_fyp.iu.LoginScreen
import com.example.trackie_fyp.iu.SignUpScreen
import com.example.trackie_fyp.models.UserViewModel
import com.example.trackie_fyp.notification.BudgetCheckWorker
import com.example.trackie_fyp.notification.MyApplication
import com.example.trackie_fyp.ui.theme.AppThemeSwitcher
import com.example.trackie_fyp.ui.theme.Trackie_FYPTheme
import com.github.mikephil.charting.utils.Utils
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize MPAndroidChart Utils
        Utils.init(this)
        // Initialize AppThemeSwitcher
        AppThemeSwitcher.init(this)
        createNotificationChannel()

        val userViewModel: UserViewModel by viewModels()

        setContent {
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
                            scheduleBudgetCheckWorker(userId)
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

    private fun scheduleBudgetCheckWorker(userId: Int) {
        val workManager = WorkManager.getInstance(this)
        val budgetCheckRequest = PeriodicWorkRequestBuilder<BudgetCheckWorker>(1, TimeUnit.DAYS)
            .setInputData(workDataOf("userId" to userId))
            .build()

        workManager.enqueueUniquePeriodicWork(
            "BudgetCheck",
            ExistingPeriodicWorkPolicy.KEEP,
            budgetCheckRequest
        )
    }




    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(MyApplication.CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}