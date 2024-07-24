package com.example.trackie_fyp.models

import androidx.lifecycle.*
import com.example.trackie_fyp.DataClass.Category
import com.example.trackie_fyp.DataClass.Expense
import com.example.trackie_fyp.DatabaseHelper
import kotlinx.coroutines.launch
import java.text.DateFormatSymbols
import java.util.*

class ReportViewModel(private val dbHelper: DatabaseHelper, private val userId: Int) : ViewModel() {
    private val _expenses = MutableLiveData<List<Expense>>()
    val expenses: LiveData<List<Expense>> get() = _expenses

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    private val _selectedMonth = MutableLiveData<Int>()
    val selectedMonth: LiveData<Int> get() = _selectedMonth

    private val _selectedYear = MutableLiveData<Int>()
    val selectedYear: LiveData<Int> get() = _selectedYear

    init {
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        _selectedMonth.value = currentMonth
        _selectedYear.value = currentYear
        loadExpensesForMonth(currentMonth, currentYear)
        loadCategories()
    }

    fun loadExpensesForMonth(month: Int, year: Int) {
        viewModelScope.launch {
            val expensesList = dbHelper.getExpensesForMonth(userId, month, year)
            _expenses.postValue(expensesList)
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            val categoriesList = dbHelper.getAllCategories(userId)
            _categories.postValue(categoriesList)
        }
    }

    fun updateSelectedMonth(month: Int) {
        _selectedMonth.value = month
        loadExpensesForMonth(month, _selectedYear.value ?: Calendar.getInstance().get(Calendar.YEAR))
    }

    fun updateSelectedYear(year: Int) {
        _selectedYear.value = year
        loadExpensesForMonth(_selectedMonth.value ?: Calendar.getInstance().get(Calendar.MONTH) + 1, year)
    }
}