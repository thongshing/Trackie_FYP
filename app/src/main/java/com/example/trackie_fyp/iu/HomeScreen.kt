package com.example.trackie_fyp.iu



import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.ExperimentalMaterial3Api


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, expenseViewModel: ExpenseViewModel = viewModel()) {
    val context = LocalContext.current
    val expenses by expenseViewModel.expenses.observeAsState(emptyList())
    val incomes by expenseViewModel.incomes.observeAsState(emptyList())

    var selectedMonth by remember { mutableStateOf(getCurrentMonth()) }
    var selectedYear by remember { mutableStateOf(getCurrentYear()) }

    LaunchedEffect(Unit) {
        expenseViewModel.loadLatestTransactions()
    }

    val filteredExpenses = expenses.filter { it.date.matchesMonthAndYear(selectedMonth, selectedYear) }.sortedByDescending { it.date.toDate() }
    val filteredIncomes = incomes.filter { it.date.matchesMonthAndYear(selectedMonth, selectedYear) }.sortedByDescending { it.date.toDate() }

    val totalIncome = filteredIncomes.sumOf { it.amount }
    val totalExpenses = filteredExpenses.sumOf { it.amount }
    val remainingBudget = totalIncome - totalExpenses

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    MonthYearDropdownMenu(selectedMonth, selectedYear, onMonthYearSelected = { month, year ->
                        selectedMonth = month
                        selectedYear = year
                    })
                }
            )
        },
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
                        onDelete = { expenseViewModel.deleteIncome(income.id) }
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
                        onDelete = { expenseViewModel.deleteExpense(expense.id) }
                    )
                    Divider()
                }
            }
        }
    )
}


@Composable
fun MonthYearDropdownMenu(
    selectedMonth: String,
    selectedYear: Int,
    onMonthYearSelected: (String, Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        TextButton(onClick = { expanded = true }) {
            Text(text = "$selectedMonth $selectedYear")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            val months = getMonthList()
            val years = getYearList()

            years.forEach { year ->
                months.forEach { month ->
                    DropdownMenuItem(
                        text = { Text(text = "$month $year") },
                        onClick = {
                            onMonthYearSelected(month, year)
                            expanded = false
                        }
                    )
                }
            }
        }
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
            val color = when (dismissState.currentValue) {
                SwipeToDismissBoxValue.EndToStart -> Color.Red
                else -> Color.Transparent
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "Delete",
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



private fun getCurrentMonth(): String {
    val dateFormat = SimpleDateFormat("MMMM", Locale.getDefault())
    return dateFormat.format(Date())
}

private fun getCurrentYear(): Int {
    val calendar = Calendar.getInstance()
    return calendar.get(Calendar.YEAR)
}

private fun getMonthList(): List<String> {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("MMMM", Locale.getDefault())
    return (0..11).map {
        calendar.set(Calendar.MONTH, it)
        dateFormat.format(calendar.time)
    }
}

private fun getYearList(): List<Int> {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    return (currentYear - 5..currentYear + 5).toList()
}

fun String.matchesMonthAndYear(month: String, year: Int): Boolean {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val date = dateFormat.parse(this)
    val monthFormat = SimpleDateFormat("MMMM", Locale.getDefault())
    val yearFormat = SimpleDateFormat("yyyy", Locale.getDefault())
    return monthFormat.format(date) == month && yearFormat.format(date).toInt() == year
}

fun String.toDate(): Date {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return dateFormat.parse(this)
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

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Trackie_FYPTheme {
        HomeScreen(navController = rememberNavController())
    }
}