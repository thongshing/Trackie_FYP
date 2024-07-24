package com.example.trackie_fyp.iu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.trackie_fyp.ui.theme.AppThemeSwitcher


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(navController: NavHostController, userId: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EnhancedButton(
            onClick = {
                navController.navigate("addIncome/$userId") {
                    popUpTo("add") { inclusive = true }
                }
            },
            text = "Add Income",
            icon = Icons.Default.AttachMoney
        )

        Spacer(modifier = Modifier.height(16.dp))

        EnhancedButton(
            onClick = {
                navController.navigate("addExpense/$userId") {
                    popUpTo("add") { inclusive = true }
                }
            },
            text = "Add Expense",
            icon = Icons.Default.ShoppingCart
        )
    }
}

@Composable
fun EnhancedButton(onClick: () -> Unit, text: String, icon: ImageVector) {
    val isDarkMode by AppThemeSwitcher.isDarkMode

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isDarkMode) Color(0xFF3677B2) else Color(0xFFD6E3EF),
            contentColor = if (isDarkMode) Color.White else Color(0xFF00021E)
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = ButtonDefaults.buttonElevation(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.labelLarge)
    }
}