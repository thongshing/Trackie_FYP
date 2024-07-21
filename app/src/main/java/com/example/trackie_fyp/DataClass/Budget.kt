package com.example.trackie_fyp.DataClass

data class Budget(
    val id: Int,
    val category: Category?,
    val amount: Double,
    val month: Int,
    val year: Int,
    val isActive: Boolean = false,
    val userId: Int // Include the userId to associate the budget with a user
)