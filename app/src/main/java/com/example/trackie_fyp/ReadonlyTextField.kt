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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadonlyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.clickable { onClick() }) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = label,
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledTextColor = Color.Black,
                disabledLabelColor = Color.Black,
                disabledBorderColor = Color.Black,
                disabledLeadingIconColor = Color.Black.copy(alpha = ContentAlpha.disabled),
                disabledTrailingIconColor = Color.Black.copy(alpha = ContentAlpha.disabled),
                disabledPlaceholderColor = Color.Black.copy(alpha = ContentAlpha.disabled),
                disabledSupportingTextColor = Color.Black.copy(alpha = ContentAlpha.disabled)
            )
        )
    }
}

