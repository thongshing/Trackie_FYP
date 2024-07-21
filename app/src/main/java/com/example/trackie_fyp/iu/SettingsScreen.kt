package com.example.trackie_fyp.iu

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trackie_fyp.models.LoginViewModel
import com.example.trackie_fyp.ui.theme.AppThemeSwitcher


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(navController: NavController, userId: Int) {
    val viewModel: LoginViewModel = viewModel()
    val isDarkMode by AppThemeSwitcher.isDarkMode

    Scaffold {
        Column(modifier = Modifier.padding(16.dp)) {
            UserProfileSection(userId)
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    SettingsOption("Add Category", navController)
                    Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .height(56.dp) // Ensuring the height is consistent
                    ) {
                        Text(text = "Dark Mode", style = MaterialTheme.typography.bodyLarge)
                        Switch(
                            checked = isDarkMode,
                            onCheckedChange = { isChecked ->
                                AppThemeSwitcher.switch(isChecked)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colorScheme.primary,
                                checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.54f),
                                uncheckedThumbColor = MaterialTheme.colorScheme.onSurface,
                                uncheckedTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.54f)
                            )
                        )
                    }
                    Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
                    SettingsOption("Feedback", navController)
                    Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
                    SettingsOption("About Us", navController)
                    Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
                    SettingsOption("Logout", navController) {
                        viewModel.logout()
                        Log.d("SettingsScreen", "User logged out, navigating to login")
                        navController.navigate("login") {
                            popUpTo("main/$userId") { inclusive = true }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserProfileSection(userId: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "User ID: $userId", style = MaterialTheme.typography.headlineSmall)
            Text(text = "User description or email", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun SettingsOption(title: String, navController: NavController, onClick: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(56.dp) // Ensuring the height is consistent
            .clickable {
                when (title) {
                    "Add Category" -> {
                        onClick?.invoke() ?: navController.navigate("categoryManagement")
                    }
                    "Reminder" -> {} // Implement navigation or actions for other options as needed
                    "Dark Mode" -> {}
                    "Feedback" -> {}
                    "About Us" -> {}
                    "Logout" -> onClick?.invoke()
                }
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically // Centering content vertically
    ) {
        Text(text = title, style = MaterialTheme.typography.bodyLarge)
        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(navController = rememberNavController(), userId = 1)
}