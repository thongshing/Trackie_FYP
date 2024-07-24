package com.example.trackie_fyp.utils

import com.github.mikephil.charting.formatter.ValueFormatter

class PercentFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return String.format("%.1f%%", value)
    }
}