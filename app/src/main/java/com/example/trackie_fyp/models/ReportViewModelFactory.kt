package com.example.trackie_fyp.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trackie_fyp.DatabaseHelper

class ReportViewModelFactory(
    private val dbHelper: DatabaseHelper,
    private val userId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReportViewModel::class.java)) {
            return ReportViewModel(dbHelper, userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}