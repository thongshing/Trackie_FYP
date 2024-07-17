package com.example.trackie_fyp.iu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.trackie_fyp.models.BudgetViewModel
import com.example.trackie_fyp.utils.DateUtils
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetStatusScreen(navController: NavHostController, budgetViewModel: BudgetViewModel = viewModel()) {
    val budgets by budgetViewModel.budgets.observeAsState(emptyList())
    val expenses by budgetViewModel.expenses.observeAsState(emptyList())
    val selectedMonth by budgetViewModel.selectedMonth.observeAsState(Calendar.getInstance().get(Calendar.MONTH) + 1)
    val selectedYear by budgetViewModel.selectedYear.observeAsState(Calendar.getInstance().get(Calendar.YEAR))

    LaunchedEffect(Unit) {
        budgetViewModel.loadBudgets()
        budgetViewModel.loadExpenses()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Budget Status") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    MonthDropdownMenu(selectedMonth) { month ->
                        budgetViewModel.updateSelectedMonth(month)
                    }
                    YearDropdownMenu(selectedYear) { year ->
                        budgetViewModel.updateSelectedYear(year)
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
                if (budgets.isEmpty()) {
                    Text("No budgets allocated for this month.")
                } else {
                    budgets.forEach { budget ->
                        val spent = expenses.filter { it.category?.id == budget.category.id }.sumOf { it.amount }
                        val remaining = budget.amount - spent
                        val progress = if (budget.amount > 0) spent / budget.amount else 0.0

                        Column(modifier = Modifier.padding(vertical = 8.dp)) {
                            Text(
                                text = "${budget.category.name}: RM ${String.format("%.2f", budget.amount)}",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Spent: RM ${String.format("%.2f", spent)}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Remaining: RM ${String.format("%.2f", remaining)}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            LinearProgressIndicator(
                                progress = progress.toFloat(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = if (remaining >= 0) Color.Green else Color.Red
                            )
                        }
                    }
                }
            }
        }
    )
}