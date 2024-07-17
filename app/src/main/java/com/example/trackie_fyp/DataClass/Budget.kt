package com.example.trackie_fyp.DataClass

data class Budget(
    val id: Int,
    val category: Category,
    val amount: Double,
    val startDate: String,
    val endDate: String
)