package com.example.trackie_fyp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ContentAlpha
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.trackie_fyp.ui.theme.AppThemeSwitcher


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadonlyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDarkMode by AppThemeSwitcher.isDarkMode

    Box(modifier = modifier.clickable { onClick() }) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = label,
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledTextColor = if (isDarkMode) Color(0xFFAEAAAA) else Color.Black,
                disabledLabelColor = if (isDarkMode) Color(0xFFAEAAAA) else Color.Black,
                disabledBorderColor = if (isDarkMode) Color(0xFFAEAAAA) else Color.Black,
                disabledLeadingIconColor = if (isDarkMode) Color(0xFFAEAAAA).copy(alpha = ContentAlpha.disabled) else Color.Black.copy(alpha = ContentAlpha.disabled),
                disabledTrailingIconColor = if (isDarkMode) Color(0xFFAEAAAA).copy(alpha = ContentAlpha.disabled) else Color.Black.copy(alpha = ContentAlpha.disabled),
                disabledPlaceholderColor = if (isDarkMode) Color(0xFFAEAAAA).copy(alpha = ContentAlpha.disabled) else Color.Black.copy(alpha = ContentAlpha.disabled),
                disabledSupportingTextColor = if (isDarkMode) Color(0xFFAEAAAA).copy(alpha = ContentAlpha.disabled) else Color.Black.copy(alpha = ContentAlpha.disabled)
            )
        )
    }
}

