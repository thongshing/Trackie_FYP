package com.example.trackie_fyp.models


import android.app.Application
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

    fun loadLatestTransactions() {
        viewModelScope.launch {
            _expenses.value = dbHelper.getLatestExpenses()
            _incomes.value = dbHelper.getLatestIncomes()
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

    fun loadExpenses() {
        viewModelScope.launch(Dispatchers.IO) {
            val expensesList = dbHelper.getAllExpenses()
            _expenses.postValue(expensesList)
        }
    }

    fun loadIncomes() {
        viewModelScope.launch(Dispatchers.IO) {
            val incomesList = dbHelper.getAllIncomes()
            _incomes.postValue(incomesList)
        }
    }

    fun saveExpense(expense: Expense) {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.addExpense(expense)
            loadExpenses()
        }
    }

    fun saveIncome(income: Income) {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.addIncome(income)
            loadIncomes()
        }
    }

    fun updateExpense(expense: Expense) {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.updateExpense(expense)
            loadExpenses()
        }
    }

    fun updateIncome(income: Income) {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.updateIncome(income)
            loadIncomes()
        }
    }

    fun deleteExpense(expenseId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.deleteExpense(expenseId)
            loadExpenses()
        }
    }

    fun deleteIncome(incomeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.deleteIncome(incomeId)
            loadIncomes()
        }
    }
}


