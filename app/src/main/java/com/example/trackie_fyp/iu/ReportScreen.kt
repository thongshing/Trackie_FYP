package com.example.trackie_fyp.iu


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.trackie_fyp.DataClass.Expense
import com.example.trackie_fyp.DatabaseHelper
import com.example.trackie_fyp.graph.LineChartComposable
import com.example.trackie_fyp.graph.PieChartComposable
import com.example.trackie_fyp.models.ReportViewModel
import com.example.trackie_fyp.models.ReportViewModelFactory
import com.example.trackie_fyp.ui.theme.AppThemeSwitcher
import com.example.trackie_fyp.utils.PercentFormatter
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import java.text.DateFormatSymbols
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(navController: NavHostController, dbHelper: DatabaseHelper, userId: Int) {
    val factory = ReportViewModelFactory(dbHelper, userId)
    val reportViewModel: ReportViewModel = viewModel(factory = factory)

    val expenses by reportViewModel.expenses.observeAsState(emptyList())
    val selectedMonth by reportViewModel.selectedMonth.observeAsState(Calendar.getInstance().get(Calendar.MONTH) + 1)
    val selectedYear by reportViewModel.selectedYear.observeAsState(Calendar.getInstance().get(Calendar.YEAR))
    var showDatePickerDialog by remember { mutableStateOf(false) }
    val isDarkMode by AppThemeSwitcher.isDarkMode

    LaunchedEffect(Unit) {
        reportViewModel.loadExpensesForMonth(selectedMonth, selectedYear)
    }

    val lineChartData = generateLineChartData(expenses, selectedMonth, selectedYear)
    val pieChartData = generatePieChartData(expenses)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Report - ${DateFormatSymbols().months[selectedMonth - 1]} $selectedYear") },
                actions = {
                    IconButton(onClick = { showDatePickerDialog = true }) {
                        Icon(Icons.Default.CalendarToday, contentDescription = "Select Month and Year")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                if (expenses.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No Analysis for This Month")
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if (isDarkMode) Color.DarkGray else Color(0xFFFFFDF8),
                                shape = MaterialTheme.shapes.medium
                            )
                            .border(
                                width = 1.dp,
                                color = Color.Transparent,
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(16.dp)
                    ) {
                        Column {
                            Text(text = "Expenses Trend", style = MaterialTheme.typography.headlineSmall)
                            Spacer(modifier = Modifier.height(16.dp))
                            LineChartComposable(lineData = lineChartData)
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if (isDarkMode) Color.DarkGray else Color(0xFFFFFDF8),
                                shape = MaterialTheme.shapes.medium
                            )
                            .border(
                                width = 1.dp,
                                color = Color.Transparent,
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(16.dp)
                    ) {
                        Column {
                            Text(text = "Category Breakdown", style = MaterialTheme.typography.headlineSmall)
                            Spacer(modifier = Modifier.height(16.dp))
                            PieChartComposable(pieData = pieChartData)
                        }
                    }
                }
            }
        }
    )

    if (showDatePickerDialog) {
        CustomMonthYearPicker(
            onDismissRequest = { showDatePickerDialog = false },
            onMonthYearSelected = { month, year ->
                reportViewModel.updateSelectedMonth(month)
                reportViewModel.updateSelectedYear(year)
                reportViewModel.loadExpensesForMonth(month, year)
                showDatePickerDialog = false
            }
        )
    }
}

fun generateLineChartData(expenses: List<Expense>, month: Int, year: Int): LineData {
    val entries = ArrayList<Entry>()
    val dailyExpenses = MutableList(31) { 0.0 }

    expenses.forEach { expense ->
        val day = expense.date.toDate()?.let { it.date } ?: return@forEach
        if (expense.date.matchesMonthAndYear(month, year)) {
            dailyExpenses[day - 1] += expense.amount
        }
    }

    dailyExpenses.forEachIndexed { index, total ->
        entries.add(Entry(index.toFloat(), total.toFloat()))
    }

    val randomColor = getRandomColor()

    val dataSet = LineDataSet(entries, "Expenses")
    dataSet.color = randomColor
    dataSet.setCircleColor(randomColor)
    dataSet.valueTextColor = android.graphics.Color.TRANSPARENT // Hide values by default
    dataSet.valueTextSize = 12f // Increase text size
    dataSet.lineWidth = 2f
    dataSet.circleRadius = 5f
    dataSet.setDrawCircleHole(false)

    return LineData(dataSet)
}

fun generatePieChartData(expenses: List<Expense>): PieData {
    val entries = ArrayList<PieEntry>()
    val groupedExpenses = expenses.groupBy { it.category?.name ?: "Unknown" }

    groupedExpenses.forEach { (category, expenses) ->
        val total = expenses.sumOf { it.amount }
        entries.add(PieEntry(total.toFloat(), category))
    }

    val dataSet = PieDataSet(entries, "Category Breakdown")

    // Set random colors for each entry
    dataSet.colors = List(entries.size) { getRandomColor() }

    dataSet.valueTextColor = android.graphics.Color.BLACK
    dataSet.valueTextSize = 12f // Increase text size

    // Enable drawing values as percentage
    dataSet.setValueFormatter(PercentFormatter())
    dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
    dataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
    dataSet.valueLinePart1Length = 0.2f
    dataSet.valueLinePart2Length = 0.4f
    dataSet.valueLineWidth = 2f
    dataSet.valueLineColor = android.graphics.Color.BLACK

    return PieData(dataSet).apply {
        setValueFormatter(PercentFormatter())
    }
}

fun getRandomColor(): Int {
    val rnd = java.util.Random()
    return android.graphics.Color.argb(
        255,
        rnd.nextInt(256),
        rnd.nextInt(256),
        rnd.nextInt(256)
    )
}





//android.graphics.Color
