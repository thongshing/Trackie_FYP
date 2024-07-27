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

    private val _autoRepeatEnabled = MutableLiveData<Boolean>()
    val autoRepeatEnabled: LiveData<Boolean> get() = _autoRepeatEnabled

    init {
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        _selectedMonth.value = currentMonth
        _selectedYear.value = currentYear
        loadBudgets()
        loadExpenses()
    }


    fun loadBudgets() {
        val month = _selectedMonth.value ?: return
        val year = _selectedYear.value ?: return
        viewModelScope.launch {
            _budgets.postValue(dbHelper.getBudgetsForMonth(userId, month, year))
            _totalBudget.postValue(dbHelper.getTotalBudgetForMonth(userId, month, year))
            _monthlyIncome.postValue(dbHelper.getIncomeForMonth(month, year, userId))
        }
    }

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

    fun loadExpenses() {
        val month = _selectedMonth.value ?: return
        val year = _selectedYear.value ?: return
        viewModelScope.launch {
            _expenses.postValue(dbHelper.getExpensesForMonth(userId, month, year))
        }
    }

    fun updateSelectedMonth(month: Int) {
        _selectedMonth.value = month
        loadBudgets()
        loadExpenses()
    }

    fun updateSelectedYear(year: Int) {
        _selectedYear.value = year
        loadBudgets()
        loadExpenses()
    }

    fun loadExpenseCategories() {
        viewModelScope.launch {
            _categories.postValue(dbHelper.getExpenseCategories(userId))
        }
    }


    fun checkCurrentMonthBudget(month: Int, year: Int) {
        viewModelScope.launch {
            _budgetExists.postValue(dbHelper.doesBudgetExist(month, year, userId))
        }
    }

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
            loadExpenses()
        }
    }

//    // Set auto-repeat flag for the current month's budgets
//    fun setAutoRepeat(checked: Boolean) {
//        viewModelScope.launch {
//            val month = _selectedMonth.value ?: return@launch
//            val year = _selectedYear.value ?: return@launch
//            dbHelper.setAutoRepeatBudget(userId, month, year, checked)
//            if (!checked) {
//                deleteNextMonthBudgets(month, year)
//            }
//        }
//    }
//
//    // Generate next month's budgets based on the current month's budgets with the auto-repeat flag
//    fun generateNextMonthBudget() {
//        viewModelScope.launch {
//            val currentMonth = _selectedMonth.value ?: return@launch
//            val currentYear = _selectedYear.value ?: return@launch
//
//            val nextMonth = (currentMonth % 12) + 1
//            val nextYear = if (nextMonth == 1) currentYear + 1 else currentYear
//
//            val budgetsToRepeat = dbHelper.getAutoRepeatBudgets(userId, currentMonth, currentYear)
//            budgetsToRepeat.forEach { budget ->
//                budget.category?.let {
//                    dbHelper.saveCategoryBudget(
//                        categoryId = it.id,
//                        amount = budget.amount,
//                        month = nextMonth,
//                        year = nextYear,
//                        userId = userId
//                    )
//                }
//            }
//            loadBudgets()
//            loadExpenses()
//        }
//    }
//
//    // Delete next month's budgets if the auto-repeat flag is unchecked
//    private fun deleteNextMonthBudgets(month: Int, year: Int) {
//        viewModelScope.launch {
//            val nextMonth = (month % 12) + 1
//            val nextYear = if (nextMonth == 1) year + 1 else year
//            dbHelper.deleteBudgetsForMonth(userId, nextMonth, nextYear)
//        }
//    }


    // Generate next month's budgets based on the current month's budgets with the auto-repeat flag
    fun generateNextMonthBudget() {
        viewModelScope.launch {
            val currentMonth = _selectedMonth.value ?: return@launch
            val currentYear = _selectedYear.value ?: return@launch

            val nextMonth = (currentMonth % 12) + 1
            val nextYear = if (nextMonth == 1) currentYear + 1 else currentYear

            val budgetsToRepeat = dbHelper.getAutoRepeatBudgets(userId, currentMonth, currentYear)
            budgetsToRepeat.forEach { budget ->
                budget.category?.let {
                    dbHelper.saveCategoryBudget(
                        categoryId = it.id,
                        amount = budget.amount,
                        month = nextMonth,
                        year = nextYear,
                        userId = userId
                    )
                }
            }
            loadBudgets()
            loadExpenses()
        }
    }

    // Delete next month's budgets if the auto-repeat flag is unchecked
    private fun deleteNextMonthBudgets(month: Int, year: Int) {
        viewModelScope.launch {
            val nextMonth = (month % 12) + 1
            val nextYear = if (nextMonth == 1) year + 1 else year
            dbHelper.deleteBudgetsForMonth(userId, nextMonth, nextYear)
        }
    }


    fun loadAutoRepeatState(month: Int, year: Int) {
        viewModelScope.launch {
            _autoRepeatEnabled.postValue(dbHelper.isBudgetAutoRepeatEnabled(userId, month, year))
        }
    }

    fun updateAutoRepeatState(checked: Boolean) {
        val month = _selectedMonth.value ?: return
        val year = _selectedYear.value ?: return
        viewModelScope.launch {
            dbHelper.setAutoRepeatBudget(userId, month, year, checked)
            _autoRepeatEnabled.value = checked
            if (!checked) {
                deleteNextMonthBudgets(month, year)
            }
        }
    }
}