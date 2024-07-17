package com.example.trackie_fyp.DataClass

data class Income(
    val id: Int,
    val amount: Double,
    val source: String,
    val description: String,
    val date: String,
    val budgetId: Int? = null, // Default value for budgetId
    val category: Category? = null // Default value for category
)
