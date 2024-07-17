package com.example.trackie_fyp.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackie_fyp.DataClass.Budget
import com.example.trackie_fyp.DataClass.Category
import com.example.trackie_fyp.DataClass.Expense
import com.example.trackie_fyp.DatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class BudgetViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    private val _budgets = MutableLiveData<List<Budget>>()
    val budgets: LiveData<List<Budget>> get() = _budgets

    private val _totalBudget = MutableLiveData<Double>()
    val totalBudget: LiveData<Double> get() = _totalBudget

    private val _expenses = MutableLiveData<List<Expense>>()
    val expenses: LiveData<List<Expense>> get() = _expenses

    private val _selectedMonth = MutableLiveData<Int>()
    val selectedMonth: LiveData<Int> get() = _selectedMonth

    private val _selectedYear = MutableLiveData<Int>()
    val selectedYear: LiveData<Int> get() = _selectedYear

    init {
        // Set default values for the current month and year
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        _selectedMonth.value = currentMonth
        _selectedYear.value = currentYear
    }

    // Load all categories from the database
    fun loadCategories() {
        viewModelScope.launch {
            _categories.postValue(dbHelper.getAllCategories())
        }
    }

    // Load budgets for the selected month and year
    fun loadBudgets() {
        val month = _selectedMonth.value ?: return
        val year = _selectedYear.value ?: return
        viewModelScope.launch {
            _budgets.postValue(dbHelper.getBudgetsForMonth(month, year))
            _totalBudget.postValue(dbHelper.getTotalBudgetForMonth(month, year))
        }
    }

    // Save overall budget for the selected month and year
    fun saveOverallBudget(amount: Double) {
        val month = _selectedMonth.value ?: return
        val year = _selectedYear.value ?: return
        viewModelScope.launch {
            dbHelper.saveOverallBudget(amount, month, year)
            loadBudgets() // Reload budgets to update the UI
        }
    }

    // Save category-wise budgets for the selected month and year
    fun saveCategoryBudgets(categoryBudgets: Map<Category, String>) {
        val month = _selectedMonth.value ?: return
        val year = _selectedYear.value ?: return
        viewModelScope.launch {
            categoryBudgets.forEach { (category, budget) ->
                dbHelper.saveCategoryBudget(category.id, budget.toDouble(), month, year)
            }
            loadBudgets() // Reload budgets to update the UI
        }
    }

    // Load all expenses from the database
    fun loadExpenses() {
        viewModelScope.launch {
            _expenses.postValue(dbHelper.getAllExpenses())
        }
    }

    // Update selected month and reload budgets
    fun updateSelectedMonth(month: Int) {
        _selectedMonth.value = month
        loadBudgets()
    }

    // Update selected year and reload budgets
    fun updateSelectedYear(year: Int) {
        _selectedYear.value = year
        loadBudgets()
    }
}