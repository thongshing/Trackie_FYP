package com.example.trackie_fyp.models;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010)\u001a\u00020\u00162\u0006\u0010*\u001a\u00020\u00052\u0006\u0010+\u001a\u00020\u0005J\u0016\u0010,\u001a\u00020-2\u0006\u0010*\u001a\u00020\u00052\u0006\u0010+\u001a\u00020\u0005J\u0016\u0010.\u001a\u00020-2\u0006\u0010*\u001a\u00020\u00052\u0006\u0010+\u001a\u00020\u0005J\u0018\u0010/\u001a\u00020-2\u0006\u0010*\u001a\u00020\u00052\u0006\u0010+\u001a\u00020\u0005H\u0002J\u0006\u00100\u001a\u00020-J\u0006\u00101\u001a\u00020-J\u0006\u00102\u001a\u00020-J\u0006\u00103\u001a\u00020-J\u0006\u00104\u001a\u00020-J\u001e\u00105\u001a\u00020-2\u0006\u00106\u001a\u00020\u00162\u0006\u0010*\u001a\u00020\u00052\u0006\u0010+\u001a\u00020\u0005J\u001a\u00107\u001a\u00020-2\u0012\u00108\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020:09J\u000e\u0010;\u001a\u00020-2\u0006\u0010<\u001a\u00020\tJ\u000e\u0010=\u001a\u00020-2\u0006\u0010*\u001a\u00020\u0005J\u000e\u0010>\u001a\u00020-2\u0006\u0010+\u001a\u00020\u0005R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00050\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\t0\u00188F\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u001d\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u00188F\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u001aR\u001d\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u000b0\u00188F\u00a2\u0006\u0006\u001a\u0004\b\u001e\u0010\u001aR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000b0\u00188F\u00a2\u0006\u0006\u001a\u0004\b \u0010\u001aR\u0019\u0010!\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u00188F\u00a2\u0006\u0006\u001a\u0004\b\"\u0010\u001aR\u0017\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00050\u00188F\u00a2\u0006\u0006\u001a\u0004\b$\u0010\u001aR\u0017\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00050\u00188F\u00a2\u0006\u0006\u001a\u0004\b&\u0010\u001aR\u0017\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u00160\u00188F\u00a2\u0006\u0006\u001a\u0004\b(\u0010\u001aR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006?"}, d2 = {"Lcom/example/trackie_fyp/models/BudgetViewModel;", "Landroidx/lifecycle/ViewModel;", "dbHelper", "Lcom/example/trackie_fyp/DatabaseHelper;", "userId", "", "(Lcom/example/trackie_fyp/DatabaseHelper;I)V", "_budgetExists", "Landroidx/lifecycle/MutableLiveData;", "", "_budgets", "", "Lcom/example/trackie_fyp/DataClass/Budget;", "_categories", "Lcom/example/trackie_fyp/DataClass/Category;", "_expenses", "Lcom/example/trackie_fyp/DataClass/Expense;", "_monthlyIncome", "Lcom/example/trackie_fyp/DataClass/Income;", "_selectedMonth", "_selectedYear", "_totalBudget", "", "budgetExists", "Landroidx/lifecycle/LiveData;", "getBudgetExists", "()Landroidx/lifecycle/LiveData;", "budgets", "getBudgets", "categories", "getCategories", "expenses", "getExpenses", "monthlyIncome", "getMonthlyIncome", "selectedMonth", "getSelectedMonth", "selectedYear", "getSelectedYear", "totalBudget", "getTotalBudget", "calculateIncomeForMonth", "month", "year", "checkCurrentMonthBudget", "", "deleteBudget", "deleteNextMonthBudgets", "generateNextMonthBudget", "loadBudgets", "loadCategories", "loadExpenseCategories", "loadExpenses", "saveBudgetIncome", "amount", "saveCategoryBudgets", "categoryBudgets", "", "", "setAutoRepeat", "checked", "updateSelectedMonth", "updateSelectedYear", "app_debug"})
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
    
    public final void loadCategories() {
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
    
    public final void saveBudgetIncome(double amount, int month, int year) {
    }
    
    public final void checkCurrentMonthBudget(int month, int year) {
    }
    
    public final double calculateIncomeForMonth(int month, int year) {
        return 0.0;
    }
    
    public final void deleteBudget(int month, int year) {
    }
    
    public final void setAutoRepeat(boolean checked) {
    }
    
    public final void generateNextMonthBudget() {
    }
    
    private final void deleteNextMonthBudgets(int month, int year) {
    }
}