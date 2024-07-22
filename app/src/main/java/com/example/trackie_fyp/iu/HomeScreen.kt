package com.example.trackie_fyp.iu



import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trackie_fyp.DataClass.Expense
import com.example.trackie_fyp.DataClass.Income
import com.example.trackie_fyp.models.ExpenseViewModel
import com.example.trackie_fyp.ui.theme.Trackie_FYPTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.text.ParseException
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.trackie_fyp.ui.theme.AppThemeSwitcher
import com.example.trackie_fyp.ui.theme.AppThemeSwitcher.isDarkMode
import java.text.DateFormatSymbols


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, userId: Int, expenseViewModel: ExpenseViewModel = viewModel()) {
    val context = LocalContext.current
    val expenses by expenseViewModel.expenses.observeAsState(emptyList())
    val incomes by expenseViewModel.incomes.observeAsState(emptyList())
    val isDarkMode by AppThemeSwitcher.isDarkMode

    var selectedMonth by remember { mutableStateOf(getCurrentMonth()) }
    var selectedYear by remember { mutableStateOf(getCurrentYear()) }
    var showDatePickerDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        expenseViewModel.loadLatestTransactions(userId)
    }

    val filteredExpenses = expenses.filter { it.date.matchesMonthAndYear(selectedMonth, selectedYear) }
        .sortedByDescending { it.date.toDate() }
    val filteredIncomes = incomes.filter { it.date.matchesMonthAndYear(selectedMonth, selectedYear) }
        .sortedByDescending { it.date.toDate() }

    val totalIncome = filteredIncomes.sumOf { it.amount }
    val totalExpenses = filteredExpenses.sumOf { it.amount }
    val remainingBudget = totalIncome - totalExpenses
    val contentColor = if (isDarkMode) Color.LightGray else Color(0xFFB2AFA5)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transactions for ${DateFormatSymbols().months[selectedMonth - 1]} $selectedYear") },
                actions = {
                    IconButton(onClick = { showDatePickerDialog = true }) {
                        Icon(Icons.Default.CalendarToday, contentDescription = "Select Month and Year")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add") },
                containerColor = contentColor) {
                Icon(Icons.Default.Add, contentDescription = "Add Transaction")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                item {
                    SummarySection(totalIncome, totalExpenses, remainingBudget)
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    CategoryBreakdownSection(filteredExpenses)
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    Text(text = "Income Transactions", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                }
                items(filteredIncomes) { income ->
                    SwipeToDismissItem(
                        transaction = income,
                        onEdit = { navController.navigate("editIncome/${income.id}") },
                        onDelete = { expenseViewModel.deleteIncome(income.id, userId) }
                    )
                    Divider()
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    Text(text = "Expense Transactions", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                }
                items(filteredExpenses) { expense ->
                    SwipeToDismissItem(
                        transaction = expense,
                        onEdit = { navController.navigate("editExpense/${expense.id}") },
                        onDelete = { expenseViewModel.deleteExpense(expense.id, userId) }
                    )
                    Divider()
                }
            }
        }
    )

    if (showDatePickerDialog) {
        CustomMonthYearPicker(
            onDismissRequest = { showDatePickerDialog = false },
            onMonthYearSelected = { month, year ->
                selectedMonth = month
                selectedYear = year
                showDatePickerDialog = false
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Any> SwipeToDismissItem(
    transaction: T,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.EndToStart -> {
                    scope.launch {
                        onDelete()
                    }
                    true
                }
                else -> false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        modifier = Modifier
            .height(60.dp) // Set the height of the item
            .padding(vertical = 4.dp),
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        },
        content = {
            TransactionItem(transaction = transaction, onEdit = onEdit)
        }
    )
}

@Composable
fun TransactionItem(transaction: Any, onEdit: () -> Unit) {
    when (transaction) {
        is Income -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onEdit() }
                    .padding(8.dp)
                    .height(60.dp), // Ensure the height matches the SwipeToDismissItem height
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = transaction.category?.name ?: "Unknown",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = transaction.date,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                Text(
                    text = "RM ${String.format("%.2f", transaction.amount)}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Green
                )
            }
        }
        is Expense -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onEdit() }
                    .padding(8.dp)
                    .height(60.dp), // Ensure the height matches the SwipeToDismissItem height
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = transaction.category?.name ?: "Unknown",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = transaction.date,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                Text(
                    text = "RM ${String.format("%.2f", transaction.amount)}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Red
                )
            }
        }
    }
}



private fun getCurrentMonth(): Int {
    val calendar = Calendar.getInstance()
    return calendar.get(Calendar.MONTH) + 1 // Months are 0-based in Calendar
}

private fun getCurrentYear(): Int {
    val calendar = Calendar.getInstance()
    return calendar.get(Calendar.YEAR)
}


fun String.matchesMonthAndYear(month: Int, year: Int): Boolean {
    val date = this.toDate() ?: return false
    val calendar = Calendar.getInstance().apply { time = date }
    return (calendar.get(Calendar.MONTH) + 1 == month) && (calendar.get(Calendar.YEAR) == year)
}

fun String.toDate(): Date? {
    return try {
        val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        format.parse(this)
    } catch (e: ParseException) {
        e.printStackTrace()
        null
    }
}


@Composable
fun SummarySection(totalIncome: Double, totalExpenses: Double, remainingBudget: Double) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray, shape = MaterialTheme.shapes.medium)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Budget Summary", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Total Income: RM ${String.format("%.2f", totalIncome)}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Total Expenses: RM ${String.format("%.2f", totalExpenses)}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Remaining Budget: RM ${String.format("%.2f", remainingBudget)}", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun CategoryBreakdownSection(expenses: List<Expense>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Category Breakdown", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        val categoryExpenses = expenses.groupBy { it.category?.name ?: "Unknown" }
        if (categoryExpenses.isEmpty()) {
            Log.d("CategoryBreakdown", "No expenses to show")
            Text(text = "No expenses to show", style = MaterialTheme.typography.bodyLarge)
        } else {
            categoryExpenses.forEach { (category, expenses) ->
                val total = expenses.sumOf { it.amount }
                Log.d("CategoryBreakdown", "Category: $category, Total: $total")
                Text(
                    text = "$category: RM ${String.format("%.2f", total)}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

