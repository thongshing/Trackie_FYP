package com.example.trackie_fyp.graph

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.example.trackie_fyp.R

class CustomMarkerView(context: Context, layoutResource: Int) : MarkerView(context, layoutResource) {

    private val tvContent: TextView = findViewById(R.id.tvContent)

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        tvContent.text = "Value: ${e?.y}" // Customize this to show what you need
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(-(width / 2).toFloat(), -height.toFloat())
    }
}