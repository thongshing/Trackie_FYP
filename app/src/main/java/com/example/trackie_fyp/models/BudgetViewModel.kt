package com.example.trackie_fyp.models


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackie_fyp.DataClass.Budget
import com.example.trackie_fyp.DataClass.Category
import com.example.trackie_fyp.DataClass.Expense
import com.example.trackie_fyp.DataClass.Income
import com.example.trackie_fyp.DatabaseHelper
import kotlinx.coroutines.launch
import java.util.Calendar


class BudgetViewModel(private val dbHelper: DatabaseHelper, private val userId: Int) : ViewModel() {

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

    private val _monthlyIncome = MutableLiveData<Income?>()
    val monthlyIncome: LiveData<Income?> get() = _monthlyIncome

    private val _budgetExists = MutableLiveData<Boolean>()
    val budgetExists: LiveData<Boolean> get() = _budgetExists

    init {
        // Set default values for the current month and year
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        _selectedMonth.value = currentMonth
        _selectedYear.value = currentYear
    }

    // Load all expense categories from the database
    fun loadCategories() {
        viewModelScope.launch {
            _categories.postValue(dbHelper.getExpenseCategories(userId))
        }
    }

    // Load budgets for the selected month and year
    fun loadBudgets() {
        val month = _selectedMonth.value ?: return
        val year = _selectedYear.value ?: return
        viewModelScope.launch {
            _budgets.postValue(dbHelper.getBudgetsForMonth(userId, month, year))
            _totalBudget.postValue(dbHelper.getTotalBudgetForMonth(userId, month, year))
            _monthlyIncome.postValue(dbHelper.getIncomeForMonth(month, year, userId))
        }
    }


    // Save category-wise budgets for the selected month and year
    fun saveCategoryBudgets(categoryBudgets: Map<Category, String>) {
        val month = _selectedMonth.value ?: return
        val year = _selectedYear.value ?: return
        viewModelScope.launch {
            categoryBudgets.forEach { (category, budget) ->
                dbHelper.saveCategoryBudget(category.id, budget.toDouble(), month, year, userId)
            }
            loadBudgets() // Reload budgets to update the UI
        }
    }

    // Load all expenses from the database
    fun loadExpenses() {
        viewModelScope.launch {
            _expenses.postValue(dbHelper.getAllExpenses(userId))
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

    // Load only expense categories from the database
    fun loadExpenseCategories() {
        viewModelScope.launch {
            _categories.postValue(dbHelper.getExpenseCategories(userId))
        }
    }

    fun saveBudgetIncome(amount: Double, month: Int, year: Int) {
        viewModelScope.launch {
            val salaryCategoryId = dbHelper.getCategoryIdByName("Salary", userId) ?: return@launch
            val income = Income(
                amount = amount,
                date = "$year-${month.toString().padStart(2, '0')}-01",
                category = Category(salaryCategoryId, "Salary", "Income", userId),
                userId = userId
            )
            dbHelper.saveIncome(income, userId)
            dbHelper.saveCategoryBudget(salaryCategoryId, amount, month, year, userId)
            loadBudgets() // Ensure to load budgets after saving income
        }
    }

    fun checkCurrentMonthBudget(month: Int, year: Int) {
        viewModelScope.launch {
            _budgetExists.postValue(dbHelper.doesBudgetExist(month, year, userId))
        }
    }

    // Calculate total income for a specific month and year
    fun calculateIncomeForMonth(month: Int, year: Int): Double {
        return dbHelper.getTotalIncomeForMonth(userId, month, year)
    }

    fun deleteBudget(month: Int, year: Int) {
        viewModelScope.launch {
            val budgets = dbHelper.getBudgetsForMonth(userId, month, year)
            budgets.forEach { budget ->
                dbHelper.deleteBudget(budget.id)
            }
            loadBudgets()
        }
    }
}