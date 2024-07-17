package com.example.trackie_fyp.iu

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
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
import com.example.trackie_fyp.models.BudgetViewModel
import java.text.DateFormatSymbols
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(navController: NavHostController, budgetViewModel: BudgetViewModel = viewModel()) {
    val context = LocalContext.current
    var income by remember { mutableStateOf("") }
    var overallBudget by remember { mutableStateOf("") }
    var isOverallBudget by remember { mutableStateOf(false) }
    val categories by budgetViewModel.categories.observeAsState(emptyList())
    val categoryBudgets = remember { mutableStateMapOf<Category, String>() }

    val selectedMonth by budgetViewModel.selectedMonth.observeAsState(Calendar.getInstance().get(Calendar.MONTH) + 1)
    val selectedYear by budgetViewModel.selectedYear.observeAsState(Calendar.getInstance().get(Calendar.YEAR))

    LaunchedEffect(Unit) {
        budgetViewModel.loadCategories()
        budgetViewModel.loadBudgets()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MonthDropdownMenu(selectedMonth) { month ->
                budgetViewModel.updateSelectedMonth(month)
            }

            YearDropdownMenu(selectedYear) { year ->
                budgetViewModel.updateSelectedYear(year)
            }
        }

        TextField(
            value = income,
            onValueChange = { income = it },
            label = { Text("Total Monthly Income") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Checkbox(
                checked = isOverallBudget,
                onCheckedChange = { isOverallBudget = it }
            )
            Text("Set Overall Budget")
        }

        if (isOverallBudget) {
            TextField(
                value = overallBudget,
                onValueChange = { overallBudget = it },
                label = { Text("Overall Budget") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Column {
                categories.forEach { category ->
                    TextField(
                        value = categoryBudgets[category] ?: "",
                        onValueChange = { categoryBudgets[category] = it },
                        label = { Text("${category.name} Budget") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
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

        Button(
            onClick = {
                if (isOverallBudget) {
                    budgetViewModel.saveOverallBudget(overallBudget.toDouble())
                } else {
                    budgetViewModel.saveCategoryBudgets(categoryBudgets)
                }
                Toast.makeText(context, "Budget saved", Toast.LENGTH_SHORT).show()
                navController.navigate("budgetStatus") // Navigate to budget status screen after saving
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Budget")
        }
    }
}

@Composable
fun MonthDropdownMenu(selectedMonth: Int, onMonthSelected: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        TextButton(onClick = { expanded = true }) {
            Text(text = DateFormatSymbols().months[selectedMonth - 1])
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            (1..12).forEach { month ->
                DropdownMenuItem(
                    text = { Text(text = DateFormatSymbols().months[month - 1]) },
                    onClick = {
                        onMonthSelected(month)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun YearDropdownMenu(selectedYear: Int, onYearSelected: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        TextButton(onClick = { expanded = true }) {
            Text(text = selectedYear.toString())
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
            (currentYear - 10..currentYear + 10).forEach { year ->
                DropdownMenuItem(
                    text = { Text(text = year.toString()) },
                    onClick = {
                        onYearSelected(year)
                        expanded = false
                    }
                )
            }
        }
    }
}