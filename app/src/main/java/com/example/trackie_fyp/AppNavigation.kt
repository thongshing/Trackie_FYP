package com.example.trackie_fyp

import androidx.compose.runtime.Composable
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
import com.example.trackie_fyp.iu.HomeScreen
import com.example.trackie_fyp.iu.ReceiptScanningScreen
import com.example.trackie_fyp.iu.SettingsScreen
import com.example.trackie_fyp.iu.TotalAmountScreen



@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { HomeScreen(navController) }
        composable("add") { AddScreen(navController) }
        composable("addIncome") { AddIncomeScreen(navController) }
        composable("addExpense") { AddExpenseScreen(navController) }
        composable("scan") { ReceiptScanningScreen(navController) }
        composable(
            "totalAmount/{totalAmount}/{receiptDate}",
            arguments = listOf(
                navArgument("totalAmount") { type = NavType.FloatType },
                navArgument("receiptDate") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val totalAmount = backStackEntry.arguments?.getFloat("totalAmount") ?: 0f
            val receiptDate = backStackEntry.arguments?.getString("receiptDate") ?: "unknown"
            TotalAmountScreen(totalAmount = totalAmount, receiptDate = receiptDate, navController = navController)
        }
        composable("settings") { SettingsScreen(navController) }
        composable("categoryManagement") { CategoryManagementScreen(navController) }
        composable("budget") {
            BudgetScreen(navController = navController)
        }
        composable("budgetStatus") {
            BudgetStatusScreen(navController = navController)
        }
        composable(
            "editExpense/{expenseId}",
            arguments = listOf(navArgument("expenseId") { type = NavType.IntType })
        ) { backStackEntry ->
            val expenseId = backStackEntry.arguments?.getInt("expenseId") ?: -1
            AddExpenseScreen(navController, expenseId)
        }
        composable(
            route = "editIncome/{incomeId}",
            arguments = listOf(navArgument("incomeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val incomeId = backStackEntry.arguments?.getInt("incomeId") ?: -1
            AddIncomeScreen(navController = navController, incomeId = incomeId)
        }
    }
}