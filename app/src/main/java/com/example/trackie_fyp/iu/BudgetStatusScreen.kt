package com.example.trackie_fyp.iu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.trackie_fyp.DatabaseHelper
import com.example.trackie_fyp.models.BudgetViewModel
import com.example.trackie_fyp.models.BudgetViewModelFactory
import com.example.trackie_fyp.ui.theme.AppThemeSwitcher
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetStatusScreen(navController: NavHostController, dbHelper: DatabaseHelper, userId: Int) {
    val factory = BudgetViewModelFactory(dbHelper, userId)
    val budgetViewModel: BudgetViewModel = viewModel(factory = factory)
    val isDarkMode by AppThemeSwitcher.isDarkMode

    val budgets by budgetViewModel.budgets.observeAsState(emptyList())
    val expenses by budgetViewModel.expenses.observeAsState(emptyList())
    val selectedMonth by budgetViewModel.selectedMonth.observeAsState(Calendar.getInstance().get(Calendar.MONTH) + 1)
    val selectedYear by budgetViewModel.selectedYear.observeAsState(Calendar.getInstance().get(Calendar.YEAR))
    val autoRepeatActive by budgetViewModel.autoRepeatEnabled.observeAsState(false)
    var showDatePickerDialog by remember { mutableStateOf(false) }
    val contentColor = if (isDarkMode) Color.LightGray else Color(0xFFB2AFA5)
    val cardBackgroundColor = if (isDarkMode) Color.DarkGray else Color.White
    val textColor = if (isDarkMode) Color.White else Color.Black

    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    LaunchedEffect(selectedMonth, selectedYear) {
        budgetViewModel.loadBudgets()
        budgetViewModel.loadExpenses()
        budgetViewModel.loadExpenseCategories()
        budgetViewModel.loadAutoRepeatState(selectedMonth, selectedYear)  // Load auto-repeat state
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Budget Status - ${DateFormatSymbols().months[selectedMonth - 1]} $selectedYear") },
                actions = {
                    IconButton(onClick = { showDatePickerDialog = true }) {
                        Icon(Icons.Default.CalendarToday, contentDescription = "Select Month and Year")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("manageBudget?month=$selectedMonth&year=$selectedYear")
            },
                containerColor = contentColor) {
                Icon(Icons.Default.Add, contentDescription = "Add/Edit Budget")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                if (budgets.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No budgets allocated for this month.")
                    }
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(budgets.filter { it.category?.type == "Expense" }) { budget ->
                            val spent = expenses.filter {
                                val expenseDate = dateFormat.parse(it.date)
                                val calendar = Calendar.getInstance().apply { time = expenseDate }
                                it.category?.id == budget.category?.id &&
                                        calendar.get(Calendar.MONTH) + 1 == selectedMonth &&
                                        calendar.get(Calendar.YEAR) == selectedYear
                            }.sumOf { it.amount }
                            val remaining = budget.amount - spent
                            val progress = if (budget.amount > 0) spent / budget.amount else 0.0

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clickable {
                                        navController.navigate("categoryTransactions/${budget.category?.id}/$selectedMonth/$selectedYear")
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = cardBackgroundColor
                                ),
                                elevation = CardDefaults.cardElevation(4.dp),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = "${budget.category?.name}: RM ${String.format("%.2f", budget.amount)}",
                                            style = MaterialTheme.typography.bodyLarge.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = textColor
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Spent: RM ${String.format("%.2f", spent)}",
                                            style = MaterialTheme.typography.bodyMedium.copy(color = textColor)
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "Remaining: RM ${String.format("%.2f", remaining)}",
                                            style = MaterialTheme.typography.bodyMedium.copy(color = textColor)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        LinearProgressIndicator(
                                            progress = progress.toFloat(),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(8.dp)
                                                .clip(RoundedCornerShape(4.dp)),
                                            color = if (remaining >= 0) Color(0xFF93C47D) else Color(0xFFC3352B)
                                        )
                                    }
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = "Go to transactions",
                                        tint = textColor
                                    )
                                }
                            }
                        }
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Checkbox(
                        checked = autoRepeatActive,
                        onCheckedChange = { checked ->
                            budgetViewModel.updateAutoRepeatState(checked)
                            if (checked) {
                                budgetViewModel.generateNextMonthBudget()
                            }
                        }
                    )
                    Text("Auto-Repeat Budget for Next Month")
                }
            }
        }
    )

    if (showDatePickerDialog) {
        CustomMonthYearPicker(
            onDismissRequest = { showDatePickerDialog = false },
            onMonthYearSelected = { month, year ->
                budgetViewModel.updateSelectedMonth(month)
                budgetViewModel.updateSelectedYear(year)
                showDatePickerDialog = false
            }
        )
    }
}

@Composable
fun CustomMonthYearPicker(
    onDismissRequest: () -> Unit,
    onMonthYearSelected: (Int, Int) -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Select Month and Year", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 16.dp))

                val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                val yearList = (currentYear - 10..currentYear + 10).toList()
                val monthList = (1..12).map { it to DateFormatSymbols().months[it - 1] }

                var selectedMonth by remember { mutableStateOf(monthList.first().first) }
                var selectedYear by remember { mutableStateOf(currentYear) }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .height(200.dp) // Set a fixed height to make the content scrollable
                    ) {
                        item {
                            Text("Year", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(bottom = 8.dp))
                        }
                        items(yearList) { year ->
                            TextButton(onClick = { selectedYear = year }) {
                                Text(text = year.toString(), color = if (selectedYear == year) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .height(200.dp) // Set a fixed height to make the content scrollable
                    ) {
                        item {
                            Text("Month", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(bottom = 8.dp))
                        }
                        items(monthList) { (month, monthName) ->
                            TextButton(onClick = { selectedMonth = month }) {
                                Text(text = monthName, color = if (selectedMonth == month) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = {
                        onMonthYearSelected(selectedMonth, selectedYear)
                    }) {
                        Text("OK")
                    }
                }
            }
        }
    }
}