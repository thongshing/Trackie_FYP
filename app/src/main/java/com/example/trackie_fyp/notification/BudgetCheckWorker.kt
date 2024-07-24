package com.example.trackie_fyp.notification

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.trackie_fyp.DatabaseHelper
import com.example.trackie_fyp.R
import android.content.Intent
import android.provider.Settings
import android.util.Log


class BudgetCheckWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        Log.d("BudgetCheckWorker", "Worker is running")
        val userId = inputData.getInt("userId", -1)
        if (userId == -1){ Log.e("BudgetCheckWorker", "Invalid userId")
            return Result.failure()}

        val dbHelper = DatabaseHelper(applicationContext)
        val budgets = dbHelper.getAllBudgets(userId)
        val expenses = dbHelper.getAllExpenses(userId)

        budgets.forEach { budget ->
            val spent = expenses.filter { it.category?.id == budget.category?.id }.sumOf { it.amount }
            val threshold = budget.amount * 0.9 // 90% of the budget

            Log.d("BudgetCheckWorker", "Checking budget for category: ${budget.category?.name}")
            Log.d("BudgetCheckWorker", "Spent: $spent, Budget: ${budget.amount}, Threshold: $threshold")

            if (spent > budget.amount) {
                Log.d("BudgetCheckWorker", "Budget for ${budget.category?.name} exceeded")
                sendNotification(budget.category?.name ?: "Unknown", "exceeded")
            } else if (spent > threshold) {
                Log.d("BudgetCheckWorker", "Budget for ${budget.category?.name} almost reached")
                sendNotification(budget.category?.name ?: "Unknown", "almost reached")
            }else{
                Log.d("BudgetCheckWorker", "Budget for ${budget.category?.name} is within limits")
            }
        }

        return Result.success()
    }

    private fun sendNotification(categoryName: String, status: String) {
        val message = when (status) {
            "exceeded" -> "You have exceeded your budget for $categoryName."
            "almost reached" -> "You are about to reach your budget limit for $categoryName."
            else -> "Budget alert for $categoryName."
        }

        val builder = NotificationCompat.Builder(applicationContext, MyApplication.CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher) // Replace with your app's icon
            .setContentTitle("Budget Alert")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(applicationContext)) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                notify(categoryName.hashCode(), builder.build())
            } else {
                // Handle the case where permission is not granted
                Log.e("BudgetCheckWorker", "POST_NOTIFICATIONS permission not granted")
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(
                        applicationContext,
                        "Please enable notifications to receive budget alerts.",
                        Toast.LENGTH_LONG
                    ).show()

                    // Optionally, direct the user to the app's notification settings
                    val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                        putExtra(Settings.EXTRA_APP_PACKAGE, applicationContext.packageName)
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    applicationContext.startActivity(intent)
                }
            }
        }
    }
}