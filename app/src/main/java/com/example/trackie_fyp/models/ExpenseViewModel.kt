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

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val dbHelper: DatabaseHelper = DatabaseHelper.getInstance(application)
    private val _expenses = MutableLiveData<List<Expense>>()
    val expenses: LiveData<List<Expense>> get() = _expenses

    private val _incomes = MutableLiveData<List<Income>>()
    val incomes: LiveData<List<Income>> get() = _incomes

    fun loadLatestTransactions(userId: Int) {
        viewModelScope.launch {
            _expenses.value = dbHelper.getLatestExpenses(userId)
            _incomes.value = dbHelper.getLatestIncomes(userId)
        }
    }

    fun getIncomeById(id: Int): LiveData<Income?> {
        val incomeLiveData = MutableLiveData<Income?>()
        viewModelScope.launch {
            try {
                val income = dbHelper.getIncomeById(id)
                incomeLiveData.postValue(income)
            } catch (e: Exception) {
                // Log and handle the exception as needed
                incomeLiveData.postValue(null)
            }
        }
        return incomeLiveData
    }

    fun getExpenseById(id: Int): LiveData<Expense?> {
        val expenseLiveData = MutableLiveData<Expense?>()
        viewModelScope.launch {
            try {
                val expense = dbHelper.getExpenseById(id)
                expenseLiveData.postValue(expense)
            } catch (e: Exception) {
                // Log and handle the exception as needed
                expenseLiveData.postValue(null)
            }
        }
        return expenseLiveData
    }

    fun loadExpenses(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val expensesList = dbHelper.getAllExpenses(userId)
            _expenses.postValue(expensesList)
        }
    }

    fun loadIncomes(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val incomesList = dbHelper.getAllIncomes(userId)
            _incomes.postValue(incomesList)
        }
    }

    fun saveExpense(expense: Expense, userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dbHelper.addExpense(expense, userId)
                loadExpenses(userId)
            } catch (e: Exception) {
                Log.e("ExpenseViewModel", "Error saving expense", e)
            }
        }
    }

    fun saveIncome(income: Income, userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dbHelper.addIncome(income, userId)
                loadIncomes(userId)
            } catch (e: Exception) {
                Log.e("ExpenseViewModel", "Error saving income", e)
            }
        }
    }

    fun updateExpense(expense: Expense, userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.updateExpense(expense)
            loadExpenses(userId)
        }
    }

    fun updateIncome(income: Income, userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.updateIncome(income)
            loadIncomes(userId)
        }
    }

    fun deleteExpense(expenseId: Int, userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.deleteExpense(expenseId)
            loadExpenses(userId)
        }
    }

    fun deleteIncome(incomeId: Int, userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.deleteIncome(incomeId)
            loadIncomes(userId)
        }
    }
}


