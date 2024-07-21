package com.example.trackie_fyp.models;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0010\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016J\u0016\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016J\u0016\u0010\u001a\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u000e2\u0006\u0010\u001b\u001a\u00020\u0016J\u0016\u0010\u001c\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\u000e2\u0006\u0010\u001b\u001a\u00020\u0016J\u000e\u0010\u001d\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u0016J\u000e\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u0016J\u000e\u0010\u001f\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u0016J\u0016\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u0016J\u0016\u0010\"\u001a\u00020\u00142\u0006\u0010#\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\u0016J\u0016\u0010$\u001a\u00020\u00142\u0006\u0010!\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u0016J\u0016\u0010%\u001a\u00020\u00142\u0006\u0010#\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\u0016R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u000e8F\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u001d\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00070\u000e8F\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0010\u00a8\u0006&"}, d2 = {"Lcom/example/trackie_fyp/models/ExpenseViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_expenses", "Landroidx/lifecycle/MutableLiveData;", "", "Lcom/example/trackie_fyp/DataClass/Expense;", "_incomes", "Lcom/example/trackie_fyp/DataClass/Income;", "dbHelper", "Lcom/example/trackie_fyp/DatabaseHelper;", "expenses", "Landroidx/lifecycle/LiveData;", "getExpenses", "()Landroidx/lifecycle/LiveData;", "incomes", "getIncomes", "deleteExpense", "", "expenseId", "", "userId", "deleteIncome", "incomeId", "getExpenseById", "id", "getIncomeById", "loadExpenses", "loadIncomes", "loadLatestTransactions", "saveExpense", "expense", "saveIncome", "income", "updateExpense", "updateIncome", "app_debug"})
public final class ExpenseViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.example.trackie_fyp.DatabaseHelper dbHelper = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.example.trackie_fyp.DataClass.Expense>> _expenses = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.example.trackie_fyp.DataClass.Income>> _incomes = null;
    
    public ExpenseViewModel(@org.jetbrains.annotations.NotNull
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.util.List<com.example.trackie_fyp.DataClass.Expense>> getExpenses() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.util.List<com.example.trackie_fyp.DataClass.Income>> getIncomes() {
        return null;
    }
    
    public final void loadLatestTransactions(int userId) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<com.example.trackie_fyp.DataClass.Income> getIncomeById(int id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<com.example.trackie_fyp.DataClass.Expense> getExpenseById(int id) {
        return null;
    }
    
    public final void loadExpenses(int userId) {
    }
    
    public final void loadIncomes(int userId) {
    }
    
    public final void saveExpense(@org.jetbrains.annotations.NotNull
    com.example.trackie_fyp.DataClass.Expense expense, int userId) {
    }
    
    public final void saveIncome(@org.jetbrains.annotations.NotNull
    com.example.trackie_fyp.DataClass.Income income, int userId) {
    }
    
    public final void updateExpense(@org.jetbrains.annotations.NotNull
    com.example.trackie_fyp.DataClass.Expense expense, int userId) {
    }
    
    public final void updateIncome(@org.jetbrains.annotations.NotNull
    com.example.trackie_fyp.DataClass.Income income, int userId) {
    }
    
    public final void deleteExpense(int expenseId, int userId) {
    }
    
    public final void deleteIncome(int incomeId, int userId) {
    }
}