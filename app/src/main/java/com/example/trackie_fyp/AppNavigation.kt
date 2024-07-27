package com.example.trackie_fyp

import MainScreen
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.trackie_fyp.iu.AddExpenseScreen
import com.example.trackie_fyp.iu.AddIncomeScreen
import com.example.trackie_fyp.iu.AddScreen
import com.example.trackie_fyp.iu.BudgetScreen
import com.example.trackie_fyp.iu.BudgetStatusScreen
import com.example.trackie_fyp.iu.CategoryManagementScreen
import com.example.trackie_fyp.iu.CategoryTransactionsScreen
import com.example.trackie_fyp.iu.HomeScreen
import com.example.trackie_fyp.iu.LoginScreen
import com.example.trackie_fyp.iu.ReceiptScanningScreen
import com.example.trackie_fyp.iu.ReportScreen
import com.example.trackie_fyp.iu.SettingsScreen
import com.example.trackie_fyp.iu.TotalAmountScreen
import com.example.trackie_fyp.models.UserViewModel
import java.util.Calendar


@Composable
fun AppNavigation(navController: NavHostController, dbHelper: DatabaseHelper, userId: Int) {
    val userViewModel: UserViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            Log.d("AppNavigation", "Navigating to HomeScreen with userId: $userId")
            HomeScreen(navController, userId)
        }
        composable("add") { AddScreen(navController, userId) }
        composable(
            "addIncome/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userIdIncome = backStackEntry.arguments?.getInt("userId") ?: -1
            AddIncomeScreen(navController, userIdIncome)
        }
        composable(
            "addExpense/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userIdExpense = backStackEntry.arguments?.getInt("userId") ?: -1
            AddExpenseScreen(navController, userIdExpense)
        }
        composable("scan") { ReceiptScanningScreen(navController, userId) }
        composable(
            "totalAmount/{totalAmount}/{receiptDate}/{userId}",
            arguments = listOf(
                navArgument("totalAmount") { type = NavType.FloatType },
                navArgument("receiptDate") { type = NavType.StringType },
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val totalAmount = backStackEntry.arguments?.getFloat("totalAmount") ?: 0f
            val receiptDate = backStackEntry.arguments?.getString("receiptDate") ?: "unknown"
            val userId = backStackEntry.arguments?.getInt("userId") ?: -1
            TotalAmountScreen(totalAmount = totalAmount, receiptDate = receiptDate, navController = navController, userId = userId)
        }
        composable("settings") { SettingsScreen(navController, userId) }
        composable("categoryManagement") { CategoryManagementScreen(navController, userId) }
        composable("budgetStatus") {
            BudgetStatusScreen(navController = navController, dbHelper = dbHelper, userId = userId)
        }
        composable(
            "manageBudget?month={month}&year={year}",
            arguments = listOf(
                navArgument("month") { type = NavType.IntType },
                navArgument("year") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val month = backStackEntry.arguments?.getInt("month") ?: Calendar.getInstance().get(Calendar.MONTH) + 1
            val year = backStackEntry.arguments?.getInt("year") ?: Calendar.getInstance().get(Calendar.YEAR)
            BudgetScreen(navController = navController, dbHelper = dbHelper, month = month, year = year, userId = userId)
        }
        composable("categoryTransactions/{categoryId}/{month}/{year}") { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")?.toInt() ?: 0
            val month = backStackEntry.arguments?.getString("month")?.toInt() ?: 1
            val year = backStackEntry.arguments?.getString("year")?.toInt() ?: Calendar.getInstance().get(Calendar.YEAR)
            CategoryTransactionsScreen(navController, categoryId, month, year, userId)
        }

        composable(
            "editExpense/{expenseId}",
            arguments = listOf(navArgument("expenseId") { type = NavType.IntType })
        ) { backStackEntry ->
            val expenseId = backStackEntry.arguments?.getInt("expenseId") ?: -1
            AddExpenseScreen(navController, expenseId = expenseId, userId = userId)
        }
        composable(
            route = "editIncome/{incomeId}",
            arguments = listOf(navArgument("incomeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val incomeId = backStackEntry.arguments?.getInt("incomeId") ?: -1
            AddIncomeScreen(navController = navController, incomeId = incomeId, userId = userId)
        }
        composable("report") { ReportScreen(navController, dbHelper = dbHelper, userId = userId) }
        composable("login") {
            LoginScreen(navController = navController, onLoginSuccess = { newUserId ->
                userViewModel.setUserId(newUserId)
                Log.d("MainActivity", "Login successful, navigating to main with userId: $newUserId")
                navController.navigate("main/$newUserId") {
                    popUpTo("login") { inclusive = true }
                }
            })
        }
        composable(
            "main/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: -1
            Log.d("MainActivity", "Navigating to MainScreen with userId: $userId")
            MainScreen(userId = userId)
        }
    }
}



