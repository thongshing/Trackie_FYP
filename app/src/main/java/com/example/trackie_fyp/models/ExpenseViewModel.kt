package com.example.trackie_fyp.models


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.trackie_fyp.DatabaseHelper
import com.example.trackie_fyp.DataClass.Expense
import com.example.trackie_fyp.DataClass.Income
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val dbHelper: DatabaseHelper = DatabaseHelper.getInstance(application)
    private val _expenses = MutableLiveData<List<Expense>>()
    val expenses: LiveData<List<Expense>> get() = _expenses

    private val _incomes = MutableLiveData<List<Income>>()
    val incomes: LiveData<List<Income>> get() = _incomes

    fun loadLatestTransactions(userId: Int) {
        viewModelScope.launch {
            try {
                val latestExpenses = withContext(Dispatchers.IO) { dbHelper.getLatestExpenses(userId) }
                val latestIncomes = withContext(Dispatchers.IO) { dbHelper.getLatestIncomes(userId) }
                _expenses.postValue(latestExpenses)
                _incomes.postValue(latestIncomes)
            } catch (e: Exception) {
                Log.e("ExpenseViewModel", "Error loading latest transactions", e)
            }
        }
    }

    fun getIncomeById(id: Int): LiveData<Income?> {
        val incomeLiveData = MutableLiveData<Income?>()
        viewModelScope.launch {
            try {
                val income = withContext(Dispatchers.IO) { dbHelper.getIncomeById(id) }
                incomeLiveData.postValue(income)
            } catch (e: Exception) {
                Log.e("ExpenseViewModel", "Error getting income by id", e)
                incomeLiveData.postValue(null)
            }
        }
        return incomeLiveData
    }

    fun getExpenseById(id: Int): LiveData<Expense?> {
        val expenseLiveData = MutableLiveData<Expense?>()
        viewModelScope.launch {
            try {
                val expense = withContext(Dispatchers.IO) { dbHelper.getExpenseById(id) }
                expenseLiveData.postValue(expense)
            } catch (e: Exception) {
                Log.e("ExpenseViewModel", "Error getting expense by id", e)
                expenseLiveData.postValue(null)
            }
        }
        return expenseLiveData
    }

    fun loadExpenses(userId: Int) {
        viewModelScope.launch {
            try {
                val expensesList = withContext(Dispatchers.IO) { dbHelper.getAllExpenses(userId) }
                _expenses.postValue(expensesList)
            } catch (e: Exception) {
                Log.e("ExpenseViewModel", "Error loading expenses", e)
            }
        }
    }

    fun loadIncomes(userId: Int) {
        viewModelScope.launch {
            try {
                val incomesList = withContext(Dispatchers.IO) { dbHelper.getAllIncomes(userId) }
                _incomes.postValue(incomesList)
            } catch (e: Exception) {
                Log.e("ExpenseViewModel", "Error loading incomes", e)
            }
        }
    }

    fun saveExpense(expense: Expense, userId: Int) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) { dbHelper.addExpense(expense, userId) }
                loadExpenses(userId)
            } catch (e: Exception) {
                Log.e("ExpenseViewModel", "Error saving expense", e)
            }
        }
    }

    fun saveIncome(income: Income, userId: Int) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) { dbHelper.addIncome(income, userId) }
                loadIncomes(userId)
            } catch (e: Exception) {
                Log.e("ExpenseViewModel", "Error saving income", e)
            }
        }
    }

    fun updateExpense(expense: Expense, userId: Int) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) { dbHelper.updateExpense(expense) }
                loadExpenses(userId)
            } catch (e: Exception) {
                Log.e("ExpenseViewModel", "Error updating expense", e)
            }
        }
    }

    fun updateIncome(income: Income, userId: Int) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) { dbHelper.updateIncome(income) }
                loadIncomes(userId)
            } catch (e: Exception) {
                Log.e("ExpenseViewModel", "Error updating income", e)
            }
        }
    }

    fun deleteExpense(expenseId: Int, userId: Int) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) { dbHelper.deleteExpense(expenseId) }
                loadExpenses(userId)
            } catch (e: Exception) {
                Log.e("ExpenseViewModel", "Error deleting expense", e)
            }
        }
    }

    fun deleteIncome(incomeId: Int, userId: Int) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) { dbHelper.deleteIncome(incomeId) }
                loadIncomes(userId)
            } catch (e: Exception) {
                Log.e("ExpenseViewModel", "Error deleting income", e)
            }
        }
    }
}


