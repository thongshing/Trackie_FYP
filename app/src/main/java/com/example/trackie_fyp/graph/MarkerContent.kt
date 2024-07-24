package com.example.trackie_fyp.graph

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.mikephil.charting.data.Entry


@Composable
fun MarkerContent(entry: Entry) {
    Text(
        text = "Value: ${entry.y}",
        style = MaterialTheme.typography.bodyMedium,
        color = Color.Black,
        modifier = Modifier
            .background(Color.White)
            .padding(8.dp)
    )
}