package com.example.trackie_fyp.iu

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trackie_fyp.ui.theme.AppThemeSwitcher


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(navController: NavController) {
    val isDarkMode by AppThemeSwitcher.isDarkMode

    Scaffold {
        Column(modifier = Modifier.padding(16.dp)) {
            UserProfileSection()
            Spacer(modifier = Modifier.height(16.dp))
            SettingsOption("Add Category", navController)
            Divider()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text(text = "Dark Mode", style = MaterialTheme.typography.bodyLarge)
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { isChecked ->
                        AppThemeSwitcher.switch(isChecked)
                    }
                )
            }
            Divider()
            SettingsOption("Feedback", navController)
            Divider()
            SettingsOption("About Us", navController)
        }
    }
}

@Composable
fun UserProfileSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = Icons.Default.Person, contentDescription = null, modifier = Modifier.size(64.dp))
            Text(text = "Bassirou Gueye", style = MaterialTheme.typography.headlineSmall)
        }
    }
}

@Composable
fun SettingsOption(title: String, navController: NavController, onClick: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                when (title) {
                    "Add Category" -> {
                        onClick?.invoke() ?: navController.navigate("categoryManagement")
                    }
                    "Reminder" -> {} // Implement navigation or actions for other options as needed
                    "Dark Mode" -> {}
                    "Feedback" -> {}
                    "About Us" -> {}
                }
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, style = MaterialTheme.typography.bodyLarge)
        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(navController = rememberNavController())
}