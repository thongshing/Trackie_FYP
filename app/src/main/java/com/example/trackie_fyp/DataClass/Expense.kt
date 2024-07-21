package com.example.trackie_fyp.DataClass

data class Expense(
    val id: Int = 0,
    val date: String,
    val amount: Double,
    val category: Category?,
    val description: String = "",
    val budgetId: Int? = null,
    val userId: Int // Include the userId to associate the expense with a user
)
