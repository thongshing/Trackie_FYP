package com.example.trackie_fyp.iu

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.trackie_fyp.DataClass.Category
import com.example.trackie_fyp.DatabaseHelper
import com.example.trackie_fyp.ReadonlyTextField
import com.example.trackie_fyp.models.BudgetViewModel
import com.example.trackie_fyp.models.BudgetViewModelFactory
import java.text.DateFormatSymbols
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(navController: NavHostController, dbHelper: DatabaseHelper, month: Int, year: Int, userId: Int) {
    val factory = BudgetViewModelFactory(dbHelper, userId)
    val budgetViewModel: BudgetViewModel = viewModel(factory = factory)

    var income by remember { mutableStateOf("") }
    val categories by budgetViewModel.categories.observeAsState(emptyList())
    val categoryBudgets = remember { mutableStateMapOf<Category, String>() }
    val selectedMonth by budgetViewModel.selectedMonth.observeAsState(month)
    val selectedYear by budgetViewModel.selectedYear.observeAsState(year)
    val monthlyIncome by budgetViewModel.monthlyIncome.observeAsState()
    val context = LocalContext.current

    var showToast by remember { mutableStateOf(false) }
    var showDatePickerDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        budgetViewModel.updateSelectedMonth(month)
        budgetViewModel.updateSelectedYear(year)
        budgetViewModel.loadExpenseCategories()
        budgetViewModel.loadBudgets()
    }

    LaunchedEffect(monthlyIncome) {
        budgetViewModel.checkCurrentMonthBudget(selectedMonth, selectedYear)
    }

    val budgetExists by budgetViewModel.budgetExists.observeAsState()

    LaunchedEffect(budgetExists) {
        if (budgetExists == true) {
            val budgetForMonthYear =
                budgetViewModel.budgets.value?.find { it.month == selectedMonth && it.year == selectedYear }
            income = budgetForMonthYear?.amount?.toString() ?: ""
        } else {
            val calculatedIncome =
                budgetViewModel.calculateIncomeForMonth(selectedMonth, selectedYear)
            income = calculatedIncome.toString()
        }
    }

    LaunchedEffect(showToast) {
        if (showToast) {
            Toast.makeText(context, "Budget saved", Toast.LENGTH_SHORT).show()
            showToast = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manage Budget - ${DateFormatSymbols().months[selectedMonth - 1]} $selectedYear") },
                actions = {
                    IconButton(onClick = { showDatePickerDialog = true }) {
                        Icon(
                            Icons.Default.CalendarToday,
                            contentDescription = "Select Month and Year"
                        )
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
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = income,
                    onValueChange = { income = it },
                    label = { Text("Total Monthly Income") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Column {
                    categories.forEach { category ->
                        val existingBudget =
                            budgetViewModel.budgets.value?.find { it.category?.id == category.id }
                        val budgetText = existingBudget?.amount?.toString() ?: ""

                        OutlinedTextField(
                            value = categoryBudgets[category] ?: budgetText,
                            onValueChange = { categoryBudgets[category] = it },
                            label = { Text("${category.name} Budget") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                val allocatedAmount = categoryBudgets.values.sumOf { it.toDoubleOrNull() ?: 0.0 }
                val remainingAmount = (income.toDoubleOrNull() ?: 0.0) - allocatedAmount

                Text(
                    text = "Remaining: RM ${String.format("%.2f", remainingAmount)}",
                    color = if (remainingAmount >= 0) Color.Green else Color.Red,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.End)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            //val incomeAmount = income.toDoubleOrNull() ?: 0.0
//                            budgetViewModel.saveBudgetIncome(
//                                incomeAmount,
//                                selectedMonth,
//                                selectedYear
//                            )
                            budgetViewModel.saveCategoryBudgets(categoryBudgets)

                            navController.navigate("budgetStatus") // Navigate to budget status screen after saving
                            showToast = true // Trigger the toast
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Save Budget")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            budgetViewModel.deleteBudget(selectedMonth, selectedYear)
                            navController.navigate("budgetStatus") // Navigate to budget status screen after deleting
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Delete Budget")
                    }
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


