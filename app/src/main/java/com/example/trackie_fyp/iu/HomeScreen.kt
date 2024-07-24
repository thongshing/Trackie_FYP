package com.example.trackie_fyp.iu



import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Search
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
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.text.ParseException
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.trackie_fyp.ui.theme.AppThemeSwitcher
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
    var searchQuery by remember { mutableStateOf("") }

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

    val combinedTransactions = (filteredExpenses + filteredIncomes)
        .sortedByDescending { it.getDateAsDate() }
        .filter {
            when (it) {
                is Expense -> it.matchesSearchQuery(searchQuery)
                is Income -> it.matchesSearchQuery(searchQuery)
                else -> false
            }
        }

    val groupedTransactions = combinedTransactions.groupBy { it.getDateString() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${DateFormatSymbols().months[selectedMonth - 1]} $selectedYear") },
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
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        label = { Text("Search Transactions") },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = "Search Icon")
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    Text(
                        text = "Transactions",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                groupedTransactions.forEach { (date, transactions) ->
                    item {
                        TransactionBox(date, transactions, navController, expenseViewModel, userId)
                    }
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

fun Expense.matchesSearchQuery(query: String): Boolean {
    return category?.name?.contains(query, ignoreCase = true) == true || description.contains(query, ignoreCase = true)
}

fun Income.matchesSearchQuery(query: String): Boolean {
    return category?.name?.contains(query, ignoreCase = true) == true || description.contains(query, ignoreCase = true)
}


@Composable
fun TransactionBox(
    date: String,
    transactions: List<Any>,
    navController: NavHostController,
    expenseViewModel: ExpenseViewModel,
    userId: Int
) {
    val isDarkMode by AppThemeSwitcher.isDarkMode
    val backgroundColor = if (isDarkMode) Color.DarkGray else Color(0xFFFFFEFB)
    val textColor = if (isDarkMode) Color(0xFFFFFEFB) else Color(0xFF232321)
    val borderColor = if (isDarkMode) Color(0xFF303030) else Color(0xFFF1F0F0)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .border(BorderStroke(1.dp, borderColor))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = date,
                style = MaterialTheme.typography.bodyLarge.copy(color = textColor),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            transactions.forEach { transaction ->
                SwipeToDismissItem(
                    transaction = transaction,
                    onEdit = { navController.navigate(getEditRoute(transaction)) },
                    onDelete = { deleteTransaction(expenseViewModel, transaction, userId) }
                )
                Divider()
            }
        }
    }
}

private fun getEditRoute(transaction: Any): String {
    return when (transaction) {
        is Income -> "editIncome/${transaction.id}"
        is Expense -> "editExpense/${transaction.id}"
        else -> ""
    }
}

private fun deleteTransaction(expenseViewModel: ExpenseViewModel, transaction: Any, userId: Int) {
    when (transaction) {
        is Income -> expenseViewModel.deleteIncome(transaction.id, userId)
        is Expense -> expenseViewModel.deleteExpense(transaction.id, userId)
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
                        text = transaction.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                Text(
                    text = "RM ${String.format("%.2f", transaction.amount)}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF93C47D)
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
                        text = transaction.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                Text(
                    text = "RM ${String.format("%.2f", transaction.amount)}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFFC3352B)
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

fun Date.toDateString(): String {
    val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return format.format(this)
}

fun Any.getDateAsDate(): Date {
    return when (this) {
        is Income -> this.date.toDate()!!
        is Expense -> this.date.toDate()!!
        else -> throw IllegalArgumentException("Unknown transaction type")
    }
}

fun Any.getDateString(): String {
    return when (this) {
        is Income -> this.date
        is Expense -> this.date
        else -> throw IllegalArgumentException("Unknown transaction type")
    }
}

@Composable
fun SummarySection(totalIncome: Double, totalExpenses: Double, remainingBudget: Double) {
    val isDarkMode by AppThemeSwitcher.isDarkMode
    val backgroundColor = if (isDarkMode) Color.DarkGray else Color(0xFFEEEEEE)
    val textColor = if (isDarkMode) Color.White else Color(0xFF232321)
    val iconColor = if (isDarkMode) Color(0xFF4CAF50) else Color(0xFF1B5E20)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, shape = MaterialTheme.shapes.medium)
            .padding(16.dp),
//            .shadow(4.dp, shape = MaterialTheme.shapes.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Budget Summary",
            style = MaterialTheme.typography.headlineSmall.copy(color = textColor),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        SummaryItem(
            icon = Icons.Default.AttachMoney,
            label = "Total Income",
            amount = totalIncome,
            textColor = textColor,
            iconColor = iconColor
        )
        Divider(color = textColor.copy(alpha = 0.5f), thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

        SummaryItem(
            icon = Icons.Default.MoneyOff,
            label = "Total Expenses",
            amount = totalExpenses,
            textColor = textColor,
            iconColor = Color(0xFFC3352B)
        )
        Divider(color = textColor.copy(alpha = 0.5f), thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

        SummaryItem(
            icon = Icons.Default.Savings,
            label = "Remaining",
            amount = remainingBudget,
            textColor = textColor,
            iconColor = Color(0xFF3677B2)
        )
    }
}

@Composable
fun SummaryItem(
    icon: ImageVector,
    label: String,
    amount: Double,
    textColor: Color,
    iconColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge.copy(color = textColor)
            )
        }
        Text(
            text = "RM ${String.format("%.2f", amount)}",
            style = MaterialTheme.typography.bodyLarge.copy(color = textColor, fontWeight = FontWeight.Bold)
        )
    }
}


@SuppressLint("DefaultLocale")
@Composable
fun CategoryBreakdownSection(expenses: List<Expense>) {
    val isDarkMode by AppThemeSwitcher.isDarkMode
    val backgroundColor = if (isDarkMode) Color.DarkGray else Color(0xFFD8D7D2)
    val textColor = if (isDarkMode) Color.White else Color(0xFF232321)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Category Breakdown",
            style = MaterialTheme.typography.headlineSmall.copy(color = textColor),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        val categoryExpenses = expenses.groupBy { it.category?.name ?: "Unknown" }
        if (categoryExpenses.isEmpty()) {
            Log.d("CategoryBreakdown", "No expenses to show")
            Text(
                text = "No expenses to show",
                style = MaterialTheme.typography.bodyLarge.copy(color = textColor)
            )
        } else {
            categoryExpenses.forEach { (category, expenses) ->
                val total = expenses.sumOf { it.amount }
                Log.d("CategoryBreakdown", "Category: $category, Total: $total")
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = backgroundColor),
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.bodyLarge.copy(color = textColor),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "RM ${String.format("%.2f", total)}",
                            style = MaterialTheme.typography.bodyLarge.copy(color = textColor),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
