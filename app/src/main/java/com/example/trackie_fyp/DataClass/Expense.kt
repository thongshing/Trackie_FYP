package com.example.trackie_fyp.DataClass

data class Expense(
    val id: Int,
    val date: String,
    val amount: Double,
    val category: Category?,
    val description: String,
    val receiptImage: ByteArray?,
    val budgetId: Int? = null // Default value for budgetId
)

