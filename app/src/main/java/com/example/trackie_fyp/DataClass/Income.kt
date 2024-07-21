package com.example.trackie_fyp.DataClass

data class Income(
    val id: Int = 0, // Default value of 0 for a new income entry
    val amount: Double,
    val description: String = "", // Default empty description
    val date: String,
    val category: Category?,
    val userId: Int
)
