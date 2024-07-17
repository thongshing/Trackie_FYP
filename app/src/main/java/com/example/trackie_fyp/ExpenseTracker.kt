package com.example.trackie_fyp

import com.example.trackie_fyp.DataClass.Budget
import com.example.trackie_fyp.DataClass.Expense

class ExpenseTracker {
    private val expenses = mutableListOf<Expense>()
    private val budgets = mutableListOf<Budget>()

    fun addExpense(expense: Expense) {
        expenses.add(expense)
    }

    fun getAllExpenses(): List<Expense> {
        return expenses.toList() // Return a copy to prevent external modification
    }

    fun addBudget(budget: Budget) {
        budgets.add(budget)
    }

    fun getAllBudgets(): List<Budget> {
        return budgets.toList() // Return a copy to prevent external modification
    }
}