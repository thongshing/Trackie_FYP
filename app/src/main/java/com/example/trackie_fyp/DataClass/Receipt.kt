package com.example.trackie_fyp.DataClass

data class Receipt(
    val id: Int,
    val imagePath: String,
    val extractedText: String,
    val expenseId: Int
)
