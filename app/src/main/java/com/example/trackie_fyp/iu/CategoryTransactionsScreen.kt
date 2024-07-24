package com.example.trackie_fyp.iu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.trackie_fyp.DataClass.Expense
import com.example.trackie_fyp.models.ExpenseViewModel
import com.example.trackie_fyp.ui.theme.AppThemeSwitcher
import java.text.DateFormatSymbols



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryTransactionsScreen(
    navController: NavHostController,
    categoryId: Int,
    month: Int,
    year: Int,
    userId: Int,
    expenseViewModel: ExpenseViewModel = viewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        expenseViewModel.loadLatestTransactions(userId)
    }

    val expenses by expenseViewModel.expenses.observeAsState(emptyList())
    val filteredExpenses = expenses.filter {
        it.category?.id == categoryId && it.date.matchesMonthAndYear(month, year)
    }.sortedByDescending { it.date.toDate() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transactions for ${DateFormatSymbols().months[month - 1]} $year") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
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
                items(filteredExpenses) { expense ->
                    TransactionItem(expense, navController, expenseViewModel, userId)
                }
            }
        }
    )
}

@Composable
fun TransactionItem(expense: Expense, navController: NavHostController, expenseViewModel: ExpenseViewModel, userId: Int) {
    val isDarkMode by AppThemeSwitcher.isDarkMode
    val cardBackgroundColor = if (isDarkMode) Color.DarkGray else Color.White
    val textColor = if (isDarkMode) Color.White else Color.Black

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate("editExpense/${expense.id}")
            },
        colors = CardDefaults.cardColors(
            containerColor = cardBackgroundColor
        ),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Date: ${expense.date}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End)
                ) {
                    Text(
                        text = "RM ${String.format("%.2f", expense.amount)}",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Category: ${expense.category?.name}",
                style = MaterialTheme.typography.bodyMedium.copy(color = textColor)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Description: ${expense.description}",
                style = MaterialTheme.typography.bodyMedium.copy(color = textColor)
            )
        }
    }
}