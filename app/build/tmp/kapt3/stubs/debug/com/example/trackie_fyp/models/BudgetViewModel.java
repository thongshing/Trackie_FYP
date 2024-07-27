package com.example.trackie_fyp.models;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010,\u001a\u00020\u00172\u0006\u0010-\u001a\u00020\u00052\u0006\u0010.\u001a\u00020\u0005J\u0016\u0010/\u001a\u0002002\u0006\u0010-\u001a\u00020\u00052\u0006\u0010.\u001a\u00020\u0005J\u0016\u00101\u001a\u0002002\u0006\u0010-\u001a\u00020\u00052\u0006\u0010.\u001a\u00020\u0005J\u0018\u00102\u001a\u0002002\u0006\u0010-\u001a\u00020\u00052\u0006\u0010.\u001a\u00020\u0005H\u0002J\u0006\u00103\u001a\u000200J\u0016\u00104\u001a\u0002002\u0006\u0010-\u001a\u00020\u00052\u0006\u0010.\u001a\u00020\u0005J\u0006\u00105\u001a\u000200J\u0006\u00106\u001a\u000200J\u0006\u00107\u001a\u000200J\u001a\u00108\u001a\u0002002\u0012\u00109\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020;0:J\u000e\u0010<\u001a\u0002002\u0006\u0010=\u001a\u00020\tJ\u000e\u0010>\u001a\u0002002\u0006\u0010-\u001a\u00020\u0005J\u000e\u0010?\u001a\u0002002\u0006\u0010.\u001a\u00020\u0005R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\f0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\f0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00050\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00050\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\t0\u00198F\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\t0\u00198F\u00a2\u0006\u0006\u001a\u0004\b\u001d\u0010\u001bR\u001d\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u00198F\u00a2\u0006\u0006\u001a\u0004\b\u001f\u0010\u001bR\u001d\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\f0\u00198F\u00a2\u0006\u0006\u001a\u0004\b!\u0010\u001bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\f0\u00198F\u00a2\u0006\u0006\u001a\u0004\b#\u0010\u001bR\u0019\u0010$\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u00198F\u00a2\u0006\u0006\u001a\u0004\b%\u0010\u001bR\u0017\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00050\u00198F\u00a2\u0006\u0006\u001a\u0004\b\'\u0010\u001bR\u0017\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00050\u00198F\u00a2\u0006\u0006\u001a\u0004\b)\u0010\u001bR\u0017\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00170\u00198F\u00a2\u0006\u0006\u001a\u0004\b+\u0010\u001bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006@"}, d2 = {"Lcom/example/trackie_fyp/models/BudgetViewModel;", "Landroidx/lifecycle/ViewModel;", "dbHelper", "Lcom/example/trackie_fyp/DatabaseHelper;", "userId", "", "(Lcom/example/trackie_fyp/DatabaseHelper;I)V", "_autoRepeatEnabled", "Landroidx/lifecycle/MutableLiveData;", "", "_budgetExists", "_budgets", "", "Lcom/example/trackie_fyp/DataClass/Budget;", "_categories", "Lcom/example/trackie_fyp/DataClass/Category;", "_expenses", "Lcom/example/trackie_fyp/DataClass/Expense;", "_monthlyIncome", "Lcom/example/trackie_fyp/DataClass/Income;", "_selectedMonth", "_selectedYear", "_totalBudget", "", "autoRepeatEnabled", "Landroidx/lifecycle/LiveData;", "getAutoRepeatEnabled", "()Landroidx/lifecycle/LiveData;", "budgetExists", "getBudgetExists", "budgets", "getBudgets", "categories", "getCategories", "expenses", "getExpenses", "monthlyIncome", "getMonthlyIncome", "selectedMonth", "getSelectedMonth", "selectedYear", "getSelectedYear", "totalBudget", "getTotalBudget", "calculateIncomeForMonth", "month", "year", "checkCurrentMonthBudget", "", "deleteBudget", "deleteNextMonthBudgets", "generateNextMonthBudget", "loadAutoRepeatState", "loadBudgets", "loadExpenseCategories", "loadExpenses", "saveCategoryBudgets", "categoryBudgets", "", "", "updateAutoRepeatState", "checked", "updateSelectedMonth", "updateSelectedYear", "app_debug"})
public final class BudgetViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.example.trackie_fyp.DatabaseHelper dbHelper = null;
    private final int userId = 0;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.example.trackie_fyp.DataClass.Category>> _categories = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.example.trackie_fyp.DataClass.Budget>> _budgets = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.Double> _totalBudget = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.example.trackie_fyp.DataClass.Expense>> _expenses = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.Integer> _selectedMonth = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.Integer> _selectedYear = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<com.example.trackie_fyp.DataClass.Income> _monthlyIncome = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> _budgetExists = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> _autoRepeatEnabled = null;
    
    public BudgetViewModel(@org.jetbrains.annotations.NotNull
    com.example.trackie_fyp.DatabaseHelper dbHelper, int userId) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.util.List<com.example.trackie_fyp.DataClass.Category>> getCategories() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.util.List<com.example.trackie_fyp.DataClass.Budget>> getBudgets() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.Double> getTotalBudget() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.util.List<com.example.trackie_fyp.DataClass.Expense>> getExpenses() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.Integer> getSelectedMonth() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.Integer> getSelectedYear() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<com.example.trackie_fyp.DataClass.Income> getMonthlyIncome() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.Boolean> getBudgetExists() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.Boolean> getAutoRepeatEnabled() {
        return null;
    }
    
    public final void loadBudgets() {
    }
    
    public final void saveCategoryBudgets(@org.jetbrains.annotations.NotNull
    java.util.Map<com.example.trackie_fyp.DataClass.Category, java.lang.String> categoryBudgets) {
    }
    
    public final void loadExpenses() {
    }
    
    public final void updateSelectedMonth(int month) {
    }
    
    public final void updateSelectedYear(int year) {
    }
    
    public final void loadExpenseCategories() {
    }
    
    public final void checkCurrentMonthBudget(int month, int year) {
    }
    
    public final double calculateIncomeForMonth(int month, int year) {
        return 0.0;
    }
    
    public final void deleteBudget(int month, int year) {
    }
    
    public final void generateNextMonthBudget() {
    }
    
    private final void deleteNextMonthBudgets(int month, int year) {
    }
    
    public final void loadAutoRepeatState(int month, int year) {
    }
    
    public final void updateAutoRepeatState(boolean checked) {
    }
}