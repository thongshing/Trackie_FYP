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
import com.example.trackie_fyp.DataClass.Receipt

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "expenses.db"
        private const val DATABASE_VERSION = 8

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
        const val COLUMN_RECEIPT_IMAGE = "receipt_image"
        const val COLUMN_EXPENSE_BUDGET_ID = "budget_id"

        // Table and column names for Category table
        const val TABLE_CATEGORY = "Category"
        const val COLUMN_CATEGORY_ID_CATEGORY = "category_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_TYPE = "type"

        // Table and column names for Budget table
        const val TABLE_BUDGET = "Budget"
        const val COLUMN_BUDGET_ID = "budget_id"
        const val COLUMN_BUDGET_AMOUNT = "amount"
        const val COLUMN_MONTH = "month"
        const val COLUMN_YEAR = "year"
        const val COLUMN_BUDGET_CATEGORY_ID = "category_id"
        const val COLUMN_IS_OVERALL = "is_overall"

        // Table and column names for Receipt table
        const val TABLE_RECEIPT = "Receipt"
        const val COLUMN_RECEIPT_ID = "receipt_id"
        const val COLUMN_IMAGE_PATH = "image_path"
        const val COLUMN_EXTRACTED_TEXT = "extracted_text"
        const val COLUMN_EXPENSE_ID_RECEIPT = "expense_id"

        // Table and column names for Income table
        const val TABLE_INCOME = "Income"
        const val COLUMN_INCOME_ID = "income_id"
        const val COLUMN_INCOME_AMOUNT = "amount"
        const val COLUMN_INCOME_DATE = "date"
        const val COLUMN_INCOME_SOURCE = "source"
        const val COLUMN_INCOME_DESCRIPTION = "description"
        const val COLUMN_INCOME_BUDGET_ID = "budget_id"
        const val COLUMN_INCOME_CATEGORY_ID = "category_id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        Log.d("DatabaseHelper", "onCreate called")
        // SQL statements to create tables
        val createExpenseTable = """
            CREATE TABLE $TABLE_EXPENSE (
                $COLUMN_EXPENSE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_DATE TEXT,
                $COLUMN_AMOUNT REAL,
                $COLUMN_CATEGORY_ID INTEGER,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_RECEIPT_IMAGE BLOB,
                $COLUMN_EXPENSE_BUDGET_ID INTEGER,
                FOREIGN KEY ($COLUMN_CATEGORY_ID) REFERENCES $TABLE_CATEGORY($COLUMN_CATEGORY_ID_CATEGORY),
                FOREIGN KEY ($COLUMN_EXPENSE_BUDGET_ID) REFERENCES $TABLE_BUDGET($COLUMN_BUDGET_ID)
            )
        """
        val createCategoryTable = """
            CREATE TABLE $TABLE_CATEGORY (
                $COLUMN_CATEGORY_ID_CATEGORY INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_TYPE TEXT
            )
        """
        val createBudgetTable = """
        CREATE TABLE $TABLE_BUDGET (
            $COLUMN_BUDGET_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_BUDGET_CATEGORY_ID INTEGER,
            $COLUMN_BUDGET_AMOUNT REAL,
            $COLUMN_MONTH INTEGER,
            $COLUMN_YEAR INTEGER,
            $COLUMN_IS_OVERALL INTEGER,
            FOREIGN KEY ($COLUMN_BUDGET_CATEGORY_ID) REFERENCES $TABLE_CATEGORY($COLUMN_CATEGORY_ID_CATEGORY)
        )
        """
        val createReceiptTable = """
            CREATE TABLE $TABLE_RECEIPT (
                $COLUMN_RECEIPT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_IMAGE_PATH TEXT,
                $COLUMN_EXTRACTED_TEXT TEXT,
                $COLUMN_EXPENSE_ID_RECEIPT INTEGER UNIQUE,
                FOREIGN KEY ($COLUMN_EXPENSE_ID_RECEIPT) REFERENCES $TABLE_EXPENSE($COLUMN_EXPENSE_ID)
            )
        """

        val createIncomeTable = """
            CREATE TABLE $TABLE_INCOME (
                $COLUMN_INCOME_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_INCOME_AMOUNT REAL,
                $COLUMN_INCOME_DATE TEXT,
                $COLUMN_INCOME_SOURCE TEXT,
                $COLUMN_INCOME_DESCRIPTION TEXT,
                $COLUMN_INCOME_BUDGET_ID INTEGER,
                $COLUMN_INCOME_CATEGORY_ID INTEGER,
                FOREIGN KEY ($COLUMN_INCOME_BUDGET_ID) REFERENCES $TABLE_BUDGET($COLUMN_BUDGET_ID),
                FOREIGN KEY ($COLUMN_INCOME_CATEGORY_ID) REFERENCES $TABLE_CATEGORY($COLUMN_CATEGORY_ID_CATEGORY)
            )
        """

        db.execSQL(createCategoryTable)
        db.execSQL(createExpenseTable)
        db.execSQL(createBudgetTable)
        db.execSQL(createReceiptTable)
        db.execSQL(createIncomeTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EXPENSE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORY")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BUDGET")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_RECEIPT")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_INCOME")
        onCreate(db)
    }

    // Function to add a new expense record
    fun addExpense(expense: Expense) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DATE, expense.date)
            put(COLUMN_AMOUNT, expense.amount)
            put(COLUMN_CATEGORY_ID, expense.category?.id)
            put(COLUMN_DESCRIPTION, expense.description)
            put(COLUMN_RECEIPT_IMAGE, expense.receiptImage) // Store as BLOB or path
            put(COLUMN_BUDGET_ID, expense.budgetId)
        }
        db.insert(TABLE_EXPENSE, null, values)
        db.close()
    }


    // Function to retrieve all expenses
    fun getAllExpenses(): List<Expense> {
        val expenses = mutableListOf<Expense>()
        val selectQuery = "SELECT * FROM $TABLE_EXPENSE"

        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_ID))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))
                val categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                val receiptImage = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_RECEIPT_IMAGE))
                val budgetId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_ID))

                val category = getCategoryById(categoryId)
                val expense = Expense(id, date, amount, category, description, receiptImage, budgetId)
                expenses.add(expense)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return expenses
    }

    // Function to update an existing expense record
    fun updateExpense(expense: Expense) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DATE, expense.date)
            put(COLUMN_AMOUNT, expense.amount)
            put(COLUMN_CATEGORY_ID, expense.category?.id)
            put(COLUMN_DESCRIPTION, expense.description)
            put(COLUMN_RECEIPT_IMAGE, expense.receiptImage) // Store as BLOB or path
            put(COLUMN_BUDGET_ID, expense.budgetId)
        }
        db.update(TABLE_EXPENSE, values, "$COLUMN_EXPENSE_ID=?", arrayOf(expense.id.toString()))
        db.close()
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
                receiptImage = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_RECEIPT_IMAGE)),
                budgetId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_BUDGET_ID))
            )
            cursor.close()
            expense
        } else {
            cursor.close()
            null
        }
    }

    // Function to delete an expense record
    fun deleteExpense(expenseId: Int) {
        val db = writableDatabase
        db.delete(TABLE_EXPENSE, "$COLUMN_EXPENSE_ID=?", arrayOf(expenseId.toString()))
        db.close()
    }

    // Function to add a new category
    fun addCategory(category: Category) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, category.name)
            put(COLUMN_TYPE, category.type)
        }
        db.insert(TABLE_CATEGORY, null, values)
        db.close()
    }

    // Function to retrieve all categories
    fun getAllCategories(): List<Category> {
        val categories = mutableListOf<Category>()
        val selectQuery = "SELECT * FROM $TABLE_CATEGORY"

        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID_CATEGORY))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE))

                val category = Category(id, name, type)
                categories.add(category)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return categories
    }

    // Function to update an existing category
    fun updateCategory(category: Category) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, category.name)
            put(COLUMN_TYPE, category.type)
        }
        db.update(TABLE_CATEGORY, values, "$COLUMN_CATEGORY_ID_CATEGORY = ?", arrayOf(category.id.toString()))
        db.close()
    }

    // Function to delete an existing category
    fun deleteCategory(categoryId: Int) {
        val db = writableDatabase
        db.delete(TABLE_CATEGORY, "$COLUMN_CATEGORY_ID_CATEGORY = ?", arrayOf(categoryId.toString()))
        db.close()
    }


    fun saveOverallBudget(amount: Double, month: Int, year: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_BUDGET_AMOUNT, amount)
            put(COLUMN_MONTH, month)
            put(COLUMN_YEAR, year)
            put(COLUMN_IS_OVERALL, 1)
            put(COLUMN_BUDGET_CATEGORY_ID, -1) // No category for overall budget
        }
        db.insert(TABLE_BUDGET, null, values)
        db.close()
    }

    fun saveCategoryBudget(categoryId: Int, amount: Double, month: Int, year: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_BUDGET_AMOUNT, amount)
            put(COLUMN_MONTH, month)
            put(COLUMN_YEAR, year)
            put(COLUMN_IS_OVERALL, 0)
            put(COLUMN_BUDGET_CATEGORY_ID, categoryId)
        }
        db.insert(TABLE_BUDGET, null, values)
        db.close()
    }

    fun getBudgetsForMonth(month: Int, year: Int): List<Budget> {
        val budgets = mutableListOf<Budget>()
        val selectQuery = "SELECT * FROM $TABLE_BUDGET WHERE $COLUMN_MONTH = ? AND $COLUMN_YEAR = ? AND $COLUMN_IS_OVERALL = 0"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, arrayOf(month.toString(), year.toString()))
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_ID))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_AMOUNT))
                val categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_CATEGORY_ID))
                val category = getCategoryById(categoryId)
                val budget = Budget(id, category ?: Category(0, "", ""), amount, "$month/$year", "$month/$year") // Adjust as needed
                budgets.add(budget)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return budgets
    }

    fun getTotalBudgetForMonth(month: Int, year: Int): Double {
        val selectQuery = "SELECT SUM($COLUMN_BUDGET_AMOUNT) AS total FROM $TABLE_BUDGET WHERE $COLUMN_MONTH = ? AND $COLUMN_YEAR = ? AND $COLUMN_IS_OVERALL = 1"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, arrayOf(month.toString(), year.toString()))
        val total = if (cursor.moveToFirst()) {
            cursor.getDouble(cursor.getColumnIndexOrThrow("total"))
        } else {
            0.0
        }
        cursor.close()
        db.close()
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
            category = Category(id, name, type)
        }

        cursor.close()
        db.close()
        return category
    }

    fun addReceipt(receipt: Receipt) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_IMAGE_PATH, receipt.imagePath)
            put(COLUMN_EXTRACTED_TEXT, receipt.extractedText)
            put(COLUMN_EXPENSE_ID_RECEIPT, receipt.expenseId)
        }
        db.insert(TABLE_RECEIPT, null, values)
        db.close()
    }

    fun getAllReceipts(): List<Receipt> {
        val receipts = mutableListOf<Receipt>()
        val selectQuery = "SELECT * FROM $TABLE_RECEIPT"

        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RECEIPT_ID))
                val imagePath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_PATH))
                val extractedText = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXTRACTED_TEXT))
                val expenseId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_ID_RECEIPT))

                val receipt = Receipt(id, imagePath, extractedText, expenseId)
                receipts.add(receipt)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return receipts
    }

    fun getReceiptByExpenseId(expenseId: Int): Receipt? {
        val db = readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_RECEIPT WHERE $COLUMN_EXPENSE_ID_RECEIPT = ?"
        val selectionArgs = arrayOf(expenseId.toString())

        var receipt: Receipt? = null
        val cursor = db.rawQuery(selectQuery, selectionArgs)

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RECEIPT_ID))
            val imagePath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_PATH))
            val extractedText = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXTRACTED_TEXT))
            receipt = Receipt(id, imagePath, extractedText, expenseId)
        }

        cursor.close()
        db.close()
        return receipt
    }

    // Function to update an existing income record
    fun addIncome(income: Income) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_INCOME_AMOUNT, income.amount)
            put(COLUMN_INCOME_SOURCE, income.source)
            put(COLUMN_INCOME_DESCRIPTION, income.description)
            put(COLUMN_INCOME_DATE, income.date)
            put(COLUMN_INCOME_BUDGET_ID, income.budgetId)
            put(COLUMN_INCOME_CATEGORY_ID, income.category?.id)
        }
        db.insert(TABLE_INCOME, null, values)
        db.close()
    }

    fun updateIncome(income: Income) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_INCOME_AMOUNT, income.amount)
            put(COLUMN_INCOME_SOURCE, income.source)
            put(COLUMN_INCOME_DESCRIPTION, income.description)
            put(COLUMN_INCOME_DATE, income.date)
            put(COLUMN_INCOME_BUDGET_ID, income.budgetId)
            put(COLUMN_INCOME_CATEGORY_ID, income.category?.id)
        }
        db.update(TABLE_INCOME, values, "$COLUMN_INCOME_ID=?", arrayOf(income.id.toString()))
        db.close()
    }

    fun deleteIncome(incomeId: Int) {
        val db = writableDatabase
        db.delete(TABLE_INCOME, "$COLUMN_INCOME_ID=?", arrayOf(incomeId.toString()))
        db.close()
    }

    // Method to retrieve all incomes
    fun getAllIncomes(): List<Income> {
        val incomes = mutableListOf<Income>()
        val selectQuery = "SELECT * FROM $TABLE_INCOME"

        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INCOME_ID))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_INCOME_AMOUNT))
                val source = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INCOME_SOURCE))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INCOME_DESCRIPTION))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INCOME_DATE))
                val budgetId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INCOME_BUDGET_ID))
                val categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INCOME_CATEGORY_ID))

                val category = getCategoryById(categoryId)
                val income = Income(id, amount, source, description, date, budgetId, category)
                incomes.add(income)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return incomes
    }


    // Function to retrieve the latest incomes
    fun getLatestIncomes(limit: Int = 10): List<Income> {
        val incomes = mutableListOf<Income>()
        val selectQuery = "SELECT * FROM $TABLE_INCOME ORDER BY $COLUMN_INCOME_DATE DESC LIMIT $limit"

        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INCOME_ID))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_INCOME_AMOUNT))
                val source = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INCOME_SOURCE))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INCOME_DESCRIPTION))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INCOME_DATE))
                val budgetId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INCOME_BUDGET_ID))
                val categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INCOME_CATEGORY_ID))

                val category = getCategoryById(categoryId)
                val income = Income(id, amount, source, description, date, budgetId, category)
                incomes.add(income)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return incomes
    }

    // Function to retrieve the latest expenses
    fun getLatestExpenses(limit: Int = 10): List<Expense> {
        val expenses = mutableListOf<Expense>()
        val selectQuery = "SELECT * FROM $TABLE_EXPENSE ORDER BY $COLUMN_DATE DESC LIMIT $limit"

        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_ID))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))
                val categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                val receiptImage = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_RECEIPT_IMAGE))
                val budgetId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_BUDGET_ID))

                val category = getCategoryById(categoryId)
                val expense = Expense(id, date, amount, category, description, receiptImage, budgetId)
                expenses.add(expense)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return expenses
    }

    fun getIncomeById(id: Int): Income? {
        val db = readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_INCOME WHERE $COLUMN_INCOME_ID = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(id.toString()))

        return if (cursor.moveToFirst()) {
            val income = Income(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INCOME_ID)),
                amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_INCOME_AMOUNT)),
                source = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INCOME_SOURCE)),
                description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INCOME_DESCRIPTION)),
                date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INCOME_DATE)),
                budgetId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INCOME_BUDGET_ID)),
                category = getCategoryById(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INCOME_CATEGORY_ID)))
            )
            cursor.close()
            db.close()
            income
        } else {
            cursor.close()
            db.close()
            null
        }
    }

}



