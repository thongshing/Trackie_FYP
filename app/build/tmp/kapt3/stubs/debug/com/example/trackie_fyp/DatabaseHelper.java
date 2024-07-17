package com.example.trackie_fyp;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000 82\u00020\u0001:\u00018B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u0014J\u000e\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u0014J\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\b0\u001aJ\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001aJ\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000e0\u001aJ\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00110\u001aJ\u001c\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001a2\u0006\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\u0014J\u0012\u0010\"\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010#\u001a\u0004\u0018\u00010\u000b2\u0006\u0010$\u001a\u00020\u0014J\u0010\u0010%\u001a\u0004\u0018\u00010\u000e2\u0006\u0010$\u001a\u00020\u0014J\u0016\u0010&\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001a2\b\b\u0002\u0010\'\u001a\u00020\u0014J\u0016\u0010(\u001a\b\u0012\u0004\u0012\u00020\u000e0\u001a2\b\b\u0002\u0010\'\u001a\u00020\u0014J\u0010\u0010)\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0016\u001a\u00020\u0014J\u0016\u0010*\u001a\u00020+2\u0006\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\u0014J\u0010\u0010,\u001a\u00020\u00062\u0006\u0010-\u001a\u00020.H\u0016J \u0010/\u001a\u00020\u00062\u0006\u0010-\u001a\u00020.2\u0006\u00100\u001a\u00020\u00142\u0006\u00101\u001a\u00020\u0014H\u0016J&\u00102\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u00103\u001a\u00020+2\u0006\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\u0014J\u001e\u00104\u001a\u00020\u00062\u0006\u00103\u001a\u00020+2\u0006\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\u0014J\u000e\u00105\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u00106\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u00107\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e\u00a8\u00069"}, d2 = {"Lcom/example/trackie_fyp/DatabaseHelper;", "Landroid/database/sqlite/SQLiteOpenHelper;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "addCategory", "", "category", "Lcom/example/trackie_fyp/DataClass/Category;", "addExpense", "expense", "Lcom/example/trackie_fyp/DataClass/Expense;", "addIncome", "income", "Lcom/example/trackie_fyp/DataClass/Income;", "addReceipt", "receipt", "Lcom/example/trackie_fyp/DataClass/Receipt;", "deleteCategory", "categoryId", "", "deleteExpense", "expenseId", "deleteIncome", "incomeId", "getAllCategories", "", "getAllExpenses", "getAllIncomes", "getAllReceipts", "getBudgetsForMonth", "Lcom/example/trackie_fyp/DataClass/Budget;", "month", "year", "getCategoryById", "getExpenseById", "id", "getIncomeById", "getLatestExpenses", "limit", "getLatestIncomes", "getReceiptByExpenseId", "getTotalBudgetForMonth", "", "onCreate", "db", "Landroid/database/sqlite/SQLiteDatabase;", "onUpgrade", "oldVersion", "newVersion", "saveCategoryBudget", "amount", "saveOverallBudget", "updateCategory", "updateExpense", "updateIncome", "Companion", "app_debug"})
public final class DatabaseHelper extends android.database.sqlite.SQLiteOpenHelper {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String DATABASE_NAME = "expenses.db";
    private static final int DATABASE_VERSION = 8;
    @kotlin.jvm.Volatile
    @org.jetbrains.annotations.Nullable
    private static volatile com.example.trackie_fyp.DatabaseHelper INSTANCE;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String TABLE_EXPENSE = "Expense";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_EXPENSE_ID = "expense_id";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_DATE = "date";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_AMOUNT = "amount";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_CATEGORY_ID = "category_id";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_DESCRIPTION = "description";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_RECEIPT_IMAGE = "receipt_image";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_EXPENSE_BUDGET_ID = "budget_id";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String TABLE_CATEGORY = "Category";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_CATEGORY_ID_CATEGORY = "category_id";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_NAME = "name";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_TYPE = "type";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String TABLE_BUDGET = "Budget";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_BUDGET_ID = "budget_id";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_BUDGET_AMOUNT = "amount";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_MONTH = "month";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_YEAR = "year";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_BUDGET_CATEGORY_ID = "category_id";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_IS_OVERALL = "is_overall";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String TABLE_RECEIPT = "Receipt";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_RECEIPT_ID = "receipt_id";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_IMAGE_PATH = "image_path";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_EXTRACTED_TEXT = "extracted_text";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_EXPENSE_ID_RECEIPT = "expense_id";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String TABLE_INCOME = "Income";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_INCOME_ID = "income_id";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_INCOME_AMOUNT = "amount";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_INCOME_DATE = "date";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_INCOME_SOURCE = "source";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_INCOME_DESCRIPTION = "description";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_INCOME_BUDGET_ID = "budget_id";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String COLUMN_INCOME_CATEGORY_ID = "category_id";
    @org.jetbrains.annotations.NotNull
    public static final com.example.trackie_fyp.DatabaseHelper.Companion Companion = null;
    
    public DatabaseHelper(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super(null, null, null, 0);
    }
    
    @java.lang.Override
    public void onCreate(@org.jetbrains.annotations.NotNull
    android.database.sqlite.SQLiteDatabase db) {
    }
    
    @java.lang.Override
    public void onUpgrade(@org.jetbrains.annotations.NotNull
    android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    
    public final void addExpense(@org.jetbrains.annotations.NotNull
    com.example.trackie_fyp.DataClass.Expense expense) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.example.trackie_fyp.DataClass.Expense> getAllExpenses() {
        return null;
    }
    
    public final void updateExpense(@org.jetbrains.annotations.NotNull
    com.example.trackie_fyp.DataClass.Expense expense) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.example.trackie_fyp.DataClass.Expense getExpenseById(int id) {
        return null;
    }
    
    public final void deleteExpense(int expenseId) {
    }
    
    public final void addCategory(@org.jetbrains.annotations.NotNull
    com.example.trackie_fyp.DataClass.Category category) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.example.trackie_fyp.DataClass.Category> getAllCategories() {
        return null;
    }
    
    public final void updateCategory(@org.jetbrains.annotations.NotNull
    com.example.trackie_fyp.DataClass.Category category) {
    }
    
    public final void deleteCategory(int categoryId) {
    }
    
    public final void saveOverallBudget(double amount, int month, int year) {
    }
    
    public final void saveCategoryBudget(int categoryId, double amount, int month, int year) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.example.trackie_fyp.DataClass.Budget> getBudgetsForMonth(int month, int year) {
        return null;
    }
    
    public final double getTotalBudgetForMonth(int month, int year) {
        return 0.0;
    }
    
    private final com.example.trackie_fyp.DataClass.Category getCategoryById(int categoryId) {
        return null;
    }
    
    public final void addReceipt(@org.jetbrains.annotations.NotNull
    com.example.trackie_fyp.DataClass.Receipt receipt) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.example.trackie_fyp.DataClass.Receipt> getAllReceipts() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.example.trackie_fyp.DataClass.Receipt getReceiptByExpenseId(int expenseId) {
        return null;
    }
    
    public final void addIncome(@org.jetbrains.annotations.NotNull
    com.example.trackie_fyp.DataClass.Income income) {
    }
    
    public final void updateIncome(@org.jetbrains.annotations.NotNull
    com.example.trackie_fyp.DataClass.Income income) {
    }
    
    public final void deleteIncome(int incomeId) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.example.trackie_fyp.DataClass.Income> getAllIncomes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.example.trackie_fyp.DataClass.Income> getLatestIncomes(int limit) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.example.trackie_fyp.DataClass.Expense> getLatestExpenses(int limit) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.example.trackie_fyp.DataClass.Income getIncomeById(int id) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u001c\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010)\u001a\u00020#2\u0006\u0010*\u001a\u00020+R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082T\u00a2\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010#X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\'\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006,"}, d2 = {"Lcom/example/trackie_fyp/DatabaseHelper$Companion;", "", "()V", "COLUMN_AMOUNT", "", "COLUMN_BUDGET_AMOUNT", "COLUMN_BUDGET_CATEGORY_ID", "COLUMN_BUDGET_ID", "COLUMN_CATEGORY_ID", "COLUMN_CATEGORY_ID_CATEGORY", "COLUMN_DATE", "COLUMN_DESCRIPTION", "COLUMN_EXPENSE_BUDGET_ID", "COLUMN_EXPENSE_ID", "COLUMN_EXPENSE_ID_RECEIPT", "COLUMN_EXTRACTED_TEXT", "COLUMN_IMAGE_PATH", "COLUMN_INCOME_AMOUNT", "COLUMN_INCOME_BUDGET_ID", "COLUMN_INCOME_CATEGORY_ID", "COLUMN_INCOME_DATE", "COLUMN_INCOME_DESCRIPTION", "COLUMN_INCOME_ID", "COLUMN_INCOME_SOURCE", "COLUMN_IS_OVERALL", "COLUMN_MONTH", "COLUMN_NAME", "COLUMN_RECEIPT_ID", "COLUMN_RECEIPT_IMAGE", "COLUMN_TYPE", "COLUMN_YEAR", "DATABASE_NAME", "DATABASE_VERSION", "", "INSTANCE", "Lcom/example/trackie_fyp/DatabaseHelper;", "TABLE_BUDGET", "TABLE_CATEGORY", "TABLE_EXPENSE", "TABLE_INCOME", "TABLE_RECEIPT", "getInstance", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.example.trackie_fyp.DatabaseHelper getInstance(@org.jetbrains.annotations.NotNull
        android.content.Context context) {
            return null;
        }
    }
}