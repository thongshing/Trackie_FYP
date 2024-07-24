package com.example.trackie_fyp.graph


import android.content.Context
import android.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieEntry

@Composable
fun PieChartComposable(pieData: PieData) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp) // Increase the height to make the PieChart larger
    ) {
        AndroidView(
            factory = { ctx ->
                PieChart(ctx).apply {
                    data = pieData
                    description.isEnabled = false
                    setUsePercentValues(true)
                    setEntryLabelColor(Color.BLACK)
                    setEntryLabelTextSize(12f)
                    setDrawEntryLabels(true)
                    holeRadius = 60f // Adjust hole radius
                    transparentCircleRadius = 25f // Adjust transparent circle radius
                    setDrawHoleEnabled(true)
                    setHoleColor(Color.WHITE)
                    marker = CustomComposeMarkerView(ctx) { entry ->
                        MarkerContent(entry)
                    }
                    invalidate()
                }
            },
            update = { chart ->
                chart.data = pieData
                chart.invalidate()
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(1.dp)
        )
    }
}