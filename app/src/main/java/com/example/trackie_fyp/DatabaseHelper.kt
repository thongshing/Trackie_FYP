package com.example.trackie_fyp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.trackie_fyp.DataClass.Budget
import com.example.trackie_fyp.DataClass.Category
import com.example.trackie_fyp.DataClass.Expense
import com.example.trackie_fyp.DataClass.Income
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "expenses.db"
        private const val DATABASE_VERSION = 19

        @Volatile
        private var INSTANCE: DatabaseHelper? = null

        fun getInstance(context: Context): DatabaseHelper {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DatabaseHelper(context.applicationContext).also { INSTANCE = it }
            }
        }

        // Table and column names for Expense table
        const val TABLE_EXPENSE = "Expense"
        const val COLUMN_EXPENSE_ID = "expense_id"
        const val COLUMN_DATE = "date"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_CATEGORY_ID = "category_id"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_EXPENSE_BUDGET_ID = "budget_id"
        const val COLUMN_EXPENSE_USER_ID = "user_id"

        // Table and column names for Category table
        const val TABLE_CATEGORY = "Category"
        const val COLUMN_CATEGORY_ID_CATEGORY = "category_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_TYPE = "type"
        const val COLUMN_CATEGORY_USER_ID = "user_id"

        // Table and column names for Budget table
        const val TABLE_BUDGET = "Budget"
        const val COLUMN_BUDGET_ID = "budget_id"
        const val COLUMN_BUDGET_AMOUNT = "amount"
        const val COLUMN_MONTH = "month"
        const val COLUMN_YEAR = "year"
        const val COLUMN_BUDGET_CATEGORY_ID = "category_id"
        const val COLUMN_IS_ACTIVE = "is_active"
        const val COLUMN_BUDGET_USER_ID = "user_id"



        // Table and column names for User table
        const val TABLE_USER = "User"
        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"


        // Table and column names for Income table
        const val TABLE_INCOME = "Income"
        const val COLUMN_INCOME_ID = "income_id"
        const val COLUMN_INCOME_AMOUNT = "amount"
        const val COLUMN_INCOME_DATE = "date"
        const val COLUMN_INCOME_DESCRIPTION = "description"
        const val COLUMN_INCOME_CATEGORY_ID = "category_id"
        const val COLUMN_INCOME_USER_ID = "user_id"

        //const val TABLE_RECEIPT = "Receipt"

    }

    override fun onCreate(db: SQLiteDatabase) {
        Log.d("DatabaseHelper", "onCreate called")
        // SQL statements to create tables

        val createUserTable = """
            CREATE TABLE $TABLE_USER (
                $COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USERNAME UNIQUE NOT NULL,
                $COLUMN_PASSWORD TEXT NOT NULL
            )
        """.trimIndent()
        val createExpenseTable = """
            CREATE TABLE $TABLE_EXPENSE (
                $COLUMN_EXPENSE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_DATE TEXT NOT NULL,
                $COLUMN_AMOUNT REAL NOT NULL,
                $COLUMN_CATEGORY_ID INTEGER NOT NULL,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_EXPENSE_BUDGET_ID INTEGER,
                $COLUMN_EXPENSE_USER_ID INTEGER NOT NULL,
                FOREIGN KEY ($COLUMN_CATEGORY_ID) REFERENCES $TABLE_CATEGORY($COLUMN_CATEGORY_ID_CATEGORY),
                FOREIGN KEY ($COLUMN_EXPENSE_BUDGET_ID) REFERENCES $TABLE_BUDGET($COLUMN_BUDGET_ID),
                FOREIGN KEY ($COLUMN_EXPENSE_USER_ID) REFERENCES $TABLE_USER($COLUMN_USER_ID)
            )
        """
        val createCategoryTable = """
            CREATE TABLE $TABLE_CATEGORY (
                $COLUMN_CATEGORY_ID_CATEGORY INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_TYPE TEXT NOT NULL,
                $COLUMN_CATEGORY_USER_ID INTEGER NOT NULL,
                FOREIGN KEY ($COLUMN_CATEGORY_USER_ID) REFERENCES $TABLE_USER($COLUMN_USER_ID)
                
            )
        """
        val createBudgetTable = """
        CREATE TABLE $TABLE_BUDGET (
            $COLUMN_BUDGET_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_BUDGET_CATEGORY_ID INTEGER NOT NULL,
            $COLUMN_BUDGET_AMOUNT REAL NOT NULL,
            $COLUMN_MONTH INTEGER NOT NULL,
            $COLUMN_YEAR INTEGER NOT NULL,
            $COLUMN_IS_ACTIVE BOOLEAN DEFAULT 0 NOT NULL,
            $COLUMN_BUDGET_USER_ID INTEGER NOT NULL,
            FOREIGN KEY ($COLUMN_BUDGET_CATEGORY_ID) REFERENCES $TABLE_CATEGORY($COLUMN_CATEGORY_ID_CATEGORY),
            FOREIGN KEY ($COLUMN_BUDGET_USER_ID) REFERENCES $TABLE_USER($COLUMN_USER_ID)
        )
        """

        val createIncomeTable = """
            CREATE TABLE $TABLE_INCOME (
                $COLUMN_INCOME_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_INCOME_AMOUNT REAL NOT NULL,
                $COLUMN_INCOME_DATE TEXT NOT NULL,
                $COLUMN_INCOME_DESCRIPTION TEXT,
                $COLUMN_INCOME_CATEGORY_ID INTEGER NOT NULL,
                $COLUMN_INCOME_USER_ID INTEGER NOT NULL,
                FOREIGN KEY ($COLUMN_INCOME_CATEGORY_ID) REFERENCES $TABLE_CATEGORY($COLUMN_CATEGORY_ID_CATEGORY),
                FOREIGN KEY ($COLUMN_INCOME_USER_ID) REFERENCES $TABLE_USER($COLUMN_USER_ID)
            )
        """



        db.execSQL(createUserTable)
        db.execSQL(createCategoryTable)
        db.execSQL(createBudgetTable)
        db.execSQL(createExpenseTable)
        db.execSQL(createIncomeTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        db.execSQL("DROP TABLE IF EXISTS $TABLE_INCOME")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EXPENSE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BUDGET")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORY")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        //db.execSQL("DROP TABLE IF EXISTS $TABLE_RECEIPT")
        onCreate(db)


    }

    fun registerUser(username: String, password: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_PASSWORD, password)
        }
        return db.insert(TABLE_USER, null, contentValues)
    }

    fun loginUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USER,
            arrayOf(COLUMN_PASSWORD),
            "$COLUMN_USERNAME = ?",
            arrayOf(username),
            null,
            null,
            null
        )

        var userExists = false
        if (cursor.moveToFirst()) {
            val storedPassword = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
            userExists = (password == storedPassword)
        }
        cursor.close()
        return userExists
    }

    fun getUserId(username: String): Int {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USER,
            arrayOf(COLUMN_USER_ID),
            "$COLUMN_USERNAME = ?",
            arrayOf(username),
            null,
            null,
            null
        )
        var userId = -1
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID))
        }
        cursor.close()
        return userId
    }

    // Function to add a new expense record
    fun addExpense(expense: Expense, userId: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DATE, expense.date)
            put(COLUMN_AMOUNT, expense.amount)
            put(COLUMN_CATEGORY_ID, expense.category?.id)
            put(COLUMN_DESCRIPTION, expense.description)
            put(COLUMN_EXPENSE_BUDGET_ID, expense.budgetId)
            put(COLUMN_EXPENSE_USER_ID, userId)
        }
        db.insert(TABLE_EXPENSE, null, values)
    }

    // Function to retrieve all expenses
    fun getAllExpenses(userId: Int): List<Expense> {
        val expenses = mutableListOf<Expense>()
        val selectQuery = "SELECT * FROM $TABLE_EXPENSE WHERE $COLUMN_EXPENSE_USER_ID = ?"

        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, arrayOf(userId.toString()))

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_ID))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))
                val categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                val budgetId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_BUDGET_ID))

                val category = getCategoryById(categoryId)
                val expense = Expense(id, date, amount, category, description, budgetId, userId)
                expenses.add(expense)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return expenses
    }

    fun getExpenseById(id: Int): Expense? {
        val db = readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_EXPENSE WHERE $COLUMN_EXPENSE_ID = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(id.toString()))

        return if (cursor.moveToFirst()) {
            val expense = Expense(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_ID)),
                date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT)),
                category = getCategoryById(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID))),
                description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                budgetId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_BUDGET_ID)),
                userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_USER_ID))
            )
            cursor.close()
            expense
        } else {
            cursor.close()
            null
        }
    }

    // Function to update an existing expense record
    fun updateExpense(expense: Expense) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DATE, expense.date)
            put(COLUMN_AMOUNT, expense.amount)
            put(COLUMN_CATEGORY_ID, expense.category?.id)
            put(COLUMN_DESCRIPTION, expense.description)
            put(COLUMN_EXPENSE_BUDGET_ID, expense.budgetId)
            put(COLUMN_EXPENSE_USER_ID, expense.userId)
        }
        db.update(TABLE_EXPENSE, values, "$COLUMN_EXPENSE_ID=?", arrayOf(expense.id.toString()))
    }

    // Function to delete an expense record
    fun deleteExpense(expenseId: Int) {
        val db = writableDatabase
        db.delete(TABLE_EXPENSE, "$COLUMN_EXPENSE_ID=?", arrayOf(expenseId.toString()))
    }

    // Function to add a new category
    fun addCategory(category: Category) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, category.name)
            put(COLUMN_TYPE, category.type)
            put(COLUMN_CATEGORY_USER_ID, category.userId)
        }
        db.insert(TABLE_CATEGORY, null, values)
    }

    // Function to retrieve all categories
    fun getAllCategories(userId: Int): List<Category> {
        val categories = mutableListOf<Category>()
        val selectQuery = "SELECT * FROM $TABLE_CATEGORY WHERE $COLUMN_CATEGORY_USER_ID = ?"

        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, arrayOf(userId.toString()))

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID_CATEGORY))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE))
                val userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_USER_ID))

                val category = Category(id, name, type, userId)
                categories.add(category)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return categories
    }

    // Function to update an existing category
    fun updateCategory(category: Category) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, category.name)
            put(COLUMN_TYPE, category.type)
            put(COLUMN_CATEGORY_USER_ID, category.userId)
        }
        db.update(TABLE_CATEGORY, values, "$COLUMN_CATEGORY_ID_CATEGORY = ?", arrayOf(category.id.toString()))
    }

    // Function to delete an existing category
    fun deleteCategory(categoryId: Int) {
        val db = writableDatabase
        db.delete(TABLE_CATEGORY, "$COLUMN_CATEGORY_ID_CATEGORY = ?", arrayOf(categoryId.toString()))
    }

    fun saveOverallBudget(amount: Double, month: Int, year: Int, userId: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_BUDGET_AMOUNT, amount)
            put(COLUMN_MONTH, month)
            put(COLUMN_YEAR, year)
            put(COLUMN_IS_ACTIVE, 0)
            put(COLUMN_BUDGET_CATEGORY_ID, -1) // No category for overall budget
            put(COLUMN_BUDGET_USER_ID, userId)
        }
        db.insert(TABLE_BUDGET, null, values)
    }

    fun getBudgetsForMonth(userId: Int, month: Int, year: Int): List<Budget> {
        val budgets = mutableListOf<Budget>()
        val selectQuery = "SELECT * FROM $TABLE_BUDGET WHERE $COLUMN_MONTH = ? AND $COLUMN_YEAR = ? AND $COLUMN_BUDGET_USER_ID = ?"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, arrayOf(month.toString(), year.toString(), userId.toString()))

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_ID))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_AMOUNT))
                val categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_CATEGORY_ID))
                val category = getCategoryById(categoryId)
                val budget = Budget(id, category ?: Category(0, "", "", userId), amount, month, year, userId = userId, isActive = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_ACTIVE)) == 1)
                budgets.add(budget)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return budgets
    }

    fun getTotalBudgetForMonth(userId: Int, month: Int, year: Int): Double {
        val selectQuery = "SELECT SUM($COLUMN_BUDGET_AMOUNT) AS total FROM $TABLE_BUDGET WHERE $COLUMN_MONTH = ? AND $COLUMN_YEAR = ? AND $COLUMN_BUDGET_USER_ID = ?"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, arrayOf(month.toString(), year.toString(), userId.toString()))

        val total = if (cursor.moveToFirst()) {
            cursor.getDouble(cursor.getColumnIndexOrThrow("total"))
        } else {
            0.0
        }
        cursor.close()
        return total
    }

    // Function to get a Category object by its ID
    private fun getCategoryById(categoryId: Int): Category? {
        val db = readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_CATEGORY WHERE $COLUMN_CATEGORY_ID_CATEGORY = ?"
        val selectionArgs = arrayOf(categoryId.toString())

        var category: Category? = null
        val cursor = db.rawQuery(selectQuery, selectionArgs)

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID_CATEGORY))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE))
            val userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_USER_ID))
            category = Category(id, name, type, userId)
        }

        cursor.close()
        return category
    }

    // Function to add a new income record
    fun addIncome(income: Income, userId: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_INCOME_AMOUNT, income.amount)
            put(COLUMN_INCOME_DESCRIPTION, income.description)
            put(COLUMN_INCOME_DATE, income.date)
            put(COLUMN_INCOME_CATEGORY_ID, income.category?.id)
            put(COLUMN_INCOME_USER_ID, userId)
        }
        db.insert(TABLE_INCOME, null, values)
    }

    // Function to update an existing income record
    fun updateIncome(income: Income) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_INCOME_AMOUNT, income.amount)
            put(COLUMN_INCOME_DESCRIPTION, income.description)
            put(COLUMN_INCOME_DATE, income.date)
            put(COLUMN_INCOME_CATEGORY_ID, income.category?.id)
            put(COLUMN_INCOME_USER_ID, income.userId)
        }
        db.update(TABLE_INCOME, values, "$COLUMN_INCOME_ID=?", arrayOf(income.id.toString()))
    }

    // Function to delete an existing income record
    fun deleteIncome(incomeId: Int) {
        val db = writableDatabase
        db.delete(TABLE_INCOME, "$COLUMN_INCOME_ID=?", arrayOf(incomeId.toString()))
    }

    // Method to retrieve all incomes
    fun getAllIncomes(userId: Int): List<Income> {
        val incomes = mutableListOf<Income>()
        val selectQuery = "SELECT * FROM $TABLE_INCOME WHERE $COLUMN_INCOME_USER_ID = ?"

        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, arrayOf(userId.toString()))

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INCOME_ID))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_INCOME_AMOUNT))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INCOME_DESCRIPTION))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INCOME_DATE))
                val categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INCOME_CATEGORY_ID))

                val category = getCategoryById(categoryId)
                val income = Income(id, amount, description, date, category, userId)
                incomes.add(income)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return incomes
    }

    // Function to retrieve the latest incomes
    fun getLatestIncomes(userId: Int, limit: Int = 10): List<Income> {
        val incomes = mutableListOf<Income>()
        val selectQuery = "SELECT * FROM $TABLE_INCOME WHERE $COLUMN_INCOME_USER_ID = ? ORDER BY $COLUMN_INCOME_DATE DESC LIMIT ?"

        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, arrayOf(userId.toString(), limit.toString()))

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INCOME_ID))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_INCOME_AMOUNT))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INCOME_DESCRIPTION))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INCOME_DATE))
                val categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INCOME_CATEGORY_ID))

                val category = getCategoryById(categoryId)
                val income = Income(id, amount, description, date, category, userId)
                incomes.add(income)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return incomes
    }

    // Function to retrieve the latest expenses
    fun getLatestExpenses(userId: Int, limit: Int = 10): List<Expense> {
        val expenses = mutableListOf<Expense>()
        val selectQuery = "SELECT * FROM $TABLE_EXPENSE WHERE $COLUMN_EXPENSE_USER_ID = ? ORDER BY $COLUMN_DATE DESC LIMIT ?"

        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, arrayOf(userId.toString(), limit.toString()))

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_ID))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))
                val categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                val budgetId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_BUDGET_ID))

                val category = getCategoryById(categoryId)
                val expense = Expense(id, date, amount, category, description, budgetId, userId)
                expenses.add(expense)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return expenses
    }

    // Function to get an income record by ID
    fun getIncomeById(id: Int): Income? {
        val db = readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_INCOME WHERE $COLUMN_INCOME_ID = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(id.toString()))

        return if (cursor.moveToFirst()) {
            val income = Income(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INCOME_ID)),
                amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_INCOME_AMOUNT)),
                description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INCOME_DESCRIPTION)),
                date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INCOME_DATE)),
                category = getCategoryById(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INCOME_CATEGORY_ID))),
                userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INCOME_USER_ID))
            )
            cursor.close()
            income
        } else {
            cursor.close()
            null
        }
    }

    fun getIncomeForMonth(month: Int, year: Int, userId: Int): Income? {
        val db = readableDatabase
        val selectQuery = """
        SELECT * FROM $TABLE_INCOME 
        WHERE strftime('%m', $COLUMN_INCOME_DATE) = ? 
        AND strftime('%Y', $COLUMN_INCOME_DATE) = ?
        AND $COLUMN_INCOME_USER_ID = ?
        """
        val cursor = db.rawQuery(selectQuery, arrayOf(month.toString().padStart(2, '0'), year.toString(), userId.toString()))

        return if (cursor.moveToFirst()) {
            val income = Income(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INCOME_ID)),
                amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_INCOME_AMOUNT)),
                description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INCOME_DESCRIPTION)),
                date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INCOME_DATE)),
                category = getCategoryById(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INCOME_CATEGORY_ID))),
                userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INCOME_USER_ID))
            )
            cursor.close()
            income
        } else {
            cursor.close()
            null
        }
    }

    fun saveIncome(income: Income, userId: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_INCOME_AMOUNT, income.amount)
            put(COLUMN_INCOME_DATE, income.date)
            put(COLUMN_INCOME_DESCRIPTION, income.description)
            put(COLUMN_INCOME_CATEGORY_ID, income.category?.id ?: -1)
            put(COLUMN_INCOME_USER_ID, userId)
        }

        val existingIncome = getIncomeForMonthAndCategory(income.date, income.category?.id ?: -1, userId)
        if (existingIncome != null) {
            db.update(TABLE_INCOME, values, "$COLUMN_INCOME_ID=?", arrayOf(existingIncome.id.toString()))
        } else {
            db.insert(TABLE_INCOME, null, values)
        }
    }

    // Add this helper method to get existing income for the month and category
    private fun getIncomeForMonthAndCategory(date: String, categoryId: Int, userId: Int): Income? {
        val db = readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_INCOME WHERE $COLUMN_INCOME_DATE = ? AND $COLUMN_INCOME_CATEGORY_ID = ? AND $COLUMN_INCOME_USER_ID = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(date, categoryId.toString(), userId.toString()))

        val income = if (cursor.moveToFirst()) {
            Income(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INCOME_ID)),
                amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_INCOME_AMOUNT)),
                description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INCOME_DESCRIPTION)),
                date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INCOME_DATE)),
                category = getCategoryById(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INCOME_CATEGORY_ID))),
                userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INCOME_USER_ID))
            )
        } else {
            null
        }

        cursor.close()
        return income
    }

    fun getExpenseCategories(userId: Int): List<Category> {
        val categories = mutableListOf<Category>()
        val selectQuery = "SELECT * FROM $TABLE_CATEGORY WHERE $COLUMN_TYPE = 'Expense' AND $COLUMN_CATEGORY_USER_ID = ?"

        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, arrayOf(userId.toString()))

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID_CATEGORY))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE))
                val userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_USER_ID))

                val category = Category(id, name, type, userId)
                categories.add(category)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return categories
    }

    fun saveCategoryBudget(categoryId: Int, amount: Double, month: Int, year: Int, userId: Int) {
        val db = writableDatabase
        val existingBudget = getBudgetForCategory(categoryId, month, year, userId)
        val values = ContentValues().apply {
            put(COLUMN_BUDGET_AMOUNT, amount)
            put(COLUMN_MONTH, month)
            put(COLUMN_YEAR, year)
            put(COLUMN_IS_ACTIVE, 0)
            put(COLUMN_BUDGET_CATEGORY_ID, categoryId)
            put(COLUMN_BUDGET_USER_ID, userId)
        }

        val budgetId: Long = if (existingBudget != null) {
            db.update(TABLE_BUDGET, values, "$COLUMN_BUDGET_ID=?", arrayOf(existingBudget.id.toString()))
            existingBudget.id.toLong()
        } else {
            db.insert(TABLE_BUDGET, null, values)
        }

        // Update the expenses with the new budget ID
        updateExpensesWithBudgetId(categoryId, month, year, userId, budgetId)
    }

    private fun updateExpensesWithBudgetId(categoryId: Int, month: Int, year: Int, userId: Int, budgetId: Long) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_EXPENSE_BUDGET_ID, budgetId)
        }

        val cursor = db.rawQuery(
            "SELECT $COLUMN_EXPENSE_ID, $COLUMN_DATE FROM $TABLE_EXPENSE WHERE $COLUMN_CATEGORY_ID = ? AND $COLUMN_EXPENSE_USER_ID = ?",
            arrayOf(categoryId.toString(), userId.toString())
        )

        db.beginTransaction()
        try {
            while (cursor.moveToNext()) {
                val expenseId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_ID))
                val dateStr = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
                val date = parseDate(dateStr)
                if (date != null && date.get(Calendar.MONTH) + 1 == month && date.get(Calendar.YEAR) == year) {
                    Log.d("UpdateExpense", "Updating expense with ID: $expenseId for date: $dateStr with budget ID: $budgetId")
                    db.update(TABLE_EXPENSE, contentValues, "$COLUMN_EXPENSE_ID = ?", arrayOf(expenseId.toString()))
                } else {
                    Log.d("UpdateExpense", "Skipping expense with ID: $expenseId for date: $dateStr (Month: ${date?.get(Calendar.MONTH)
                        ?.plus(1)}, Year: ${date?.get(Calendar.YEAR)})")
                }
            }
            db.setTransactionSuccessful()
        } finally {
            cursor.close()
            db.endTransaction()
        }
    }

    private fun parseDate(dateStr: String): Calendar? {
        return try {
            val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val date = sdf.parse(dateStr)
            Calendar.getInstance().apply {
                time = date
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }

    private fun getBudgetForCategory(categoryId: Int, month: Int, year: Int, userId: Int): Budget? {
        val db = readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_BUDGET WHERE $COLUMN_BUDGET_CATEGORY_ID = ? AND $COLUMN_MONTH = ? AND $COLUMN_YEAR = ? AND $COLUMN_BUDGET_USER_ID = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(categoryId.toString(), month.toString(), year.toString(), userId.toString()))

        return if (cursor.moveToFirst()) {
            val budget = Budget(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_ID)),
                category = getCategoryById(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_CATEGORY_ID)))!!,
                amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_AMOUNT)),
                month = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MONTH)),
                year = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_YEAR)),
                userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_USER_ID)),
                isActive = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_ACTIVE)) == 1
            )
            cursor.close()
            budget
        } else {
            cursor.close()
            null
        }
    }

    fun getCategoryIdByName(name: String, userId: Int): Int? {
        val selectQuery = "SELECT $COLUMN_CATEGORY_ID_CATEGORY FROM $TABLE_CATEGORY WHERE $COLUMN_NAME = ? AND $COLUMN_CATEGORY_USER_ID = ?"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, arrayOf(name, userId.toString()))
        val categoryId = if (cursor.moveToFirst()) {
            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID_CATEGORY))
        } else {
            null
        }
        cursor.close()
        return categoryId
    }

    fun getTotalIncomeForMonth(userId: Int, month: Int, year: Int): Double {
        val db = this.readableDatabase
        val monthStr = month.toString().padStart(2, '0')
        val yearStr = year.toString()
        val selectQuery = """
        SELECT SUM($COLUMN_INCOME_AMOUNT) AS total 
        FROM $TABLE_INCOME 
        WHERE SUBSTR($COLUMN_INCOME_DATE, 4, 2) = ? 
          AND SUBSTR($COLUMN_INCOME_DATE, 7, 4) = ?
          AND $COLUMN_INCOME_USER_ID = ?
    """
        val cursor = db.rawQuery(selectQuery, arrayOf(monthStr, yearStr, userId.toString()))

        val totalIncome = if (cursor.moveToFirst()) {
            cursor.getDouble(cursor.getColumnIndexOrThrow("total"))
        } else {
            0.0
        }

        cursor.close()
        return totalIncome
    }

    fun doesBudgetExist(month: Int, year: Int, userId: Int): Boolean {
        val db = readableDatabase
        val selectQuery = "SELECT COUNT(*) FROM $TABLE_BUDGET WHERE $COLUMN_MONTH = ? AND $COLUMN_YEAR = ? AND $COLUMN_BUDGET_USER_ID = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(month.toString(), year.toString(), userId.toString()))

        val exists = if (cursor.moveToFirst()) {
            cursor.getInt(0) > 0
        } else {
            false
        }

        cursor.close()
        return exists
    }

    fun deleteBudget(budgetId: Int) {
        val db = writableDatabase
        db.delete(TABLE_BUDGET, "$COLUMN_BUDGET_ID = ?", arrayOf(budgetId.toString()))
    }

    fun getExpensesForMonth(userId: Int, month: Int, year: Int): List<Expense> {
        val expenses = mutableListOf<Expense>()
        val db = this.readableDatabase
        val formattedMonth = String.format("%02d", month)
        val formattedYear = year.toString()

        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_EXPENSE WHERE substr($COLUMN_DATE, 4, 2) = ? AND substr($COLUMN_DATE, 7, 4) = ? AND $COLUMN_EXPENSE_USER_ID = ?",
            arrayOf(formattedMonth, formattedYear, userId.toString())
        )

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_ID))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))
                val categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                val budgetId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_BUDGET_ID))

                val category = getCategoryById(categoryId)
                val expense = Expense(id, date, amount, category, description, budgetId, userId)
                expenses.add(expense)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return expenses
    }

    fun setAutoRepeatBudget(userId: Int, month: Int, year: Int, isAutoRepeat: Boolean) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_IS_ACTIVE, if (isAutoRepeat) 1 else 0)
        }
        db.update(
            TABLE_BUDGET,
            contentValues,
            "$COLUMN_BUDGET_USER_ID = ? AND $COLUMN_MONTH = ? AND $COLUMN_YEAR = ?",
            arrayOf(userId.toString(), month.toString(), year.toString())
        )
    }

    fun isBudgetAutoRepeatEnabled(userId: Int, month: Int, year: Int): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_BUDGET,
            arrayOf(COLUMN_IS_ACTIVE),
            "$COLUMN_USER_ID = ? AND $COLUMN_MONTH = ? AND $COLUMN_YEAR = ?",
            arrayOf(userId.toString(), month.toString(), year.toString()),
            null,
            null,
            null
        )
        var isAutoRepeatEnabled = false
        if (cursor.moveToFirst()) {
            isAutoRepeatEnabled = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_ACTIVE)) > 0
        }
        cursor.close()
        return isAutoRepeatEnabled
    }

    fun getAutoRepeatBudgets(userId: Int, month: Int, year: Int): List<Budget> {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_BUDGET,
            null,
            "$COLUMN_BUDGET_USER_ID = ? AND $COLUMN_MONTH = ? AND $COLUMN_YEAR = ? AND $COLUMN_IS_ACTIVE = 1",
            arrayOf(userId.toString(), month.toString(), year.toString()),
            null,
            null,
            null
        )

        val budgets = mutableListOf<Budget>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_ID))
                val categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_CATEGORY_ID))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_AMOUNT))
                val category = getCategoryById(categoryId)
                budgets.add(Budget(id, category, amount, month, year, true, userId))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return budgets
    }



    // Delete budgets for a specific month and year
    fun deleteBudgetsForMonth(userId: Int, month: Int, year: Int) {
        val db = writableDatabase
        db.delete(
            TABLE_BUDGET,
            "$COLUMN_BUDGET_USER_ID = ? AND $COLUMN_MONTH = ? AND $COLUMN_YEAR = ?",
            arrayOf(userId.toString(), month.toString(), year.toString())
        )
    }

    fun getBudgetIdForCategory(categoryId: Int, month: Int, year: Int, userId: Int): Int? {
        val db = readableDatabase
        val selectQuery = "SELECT $COLUMN_BUDGET_ID FROM $TABLE_BUDGET WHERE $COLUMN_BUDGET_CATEGORY_ID = ? AND $COLUMN_MONTH = ? AND $COLUMN_YEAR = ? AND $COLUMN_BUDGET_USER_ID = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(categoryId.toString(), month.toString(), year.toString(), userId.toString()))

        return if (cursor.moveToFirst()) {
            val budgetId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_ID))
            cursor.close()
            budgetId
        } else {
            cursor.close()
            null
        }
    }


    fun getAllBudgets(userId: Int): List<Budget> {
        val budgets = mutableListOf<Budget>()
        val db = readableDatabase
        val cursor = db.query(TABLE_BUDGET, null, "$COLUMN_BUDGET_USER_ID = ?", arrayOf(userId.toString()), null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val budget = Budget(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_ID)),
                    category = getCategoryById(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_CATEGORY_ID)))!!,
                    amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_AMOUNT)),
                    month = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MONTH)),
                    year = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_YEAR)),
                    userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_USER_ID)),
                    isActive = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_ACTIVE)) == 1
                )
                budgets.add(budget)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return budgets
    }


}



