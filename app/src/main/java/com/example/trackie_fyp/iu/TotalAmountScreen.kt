package com.example.trackie_fyp.iu

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.trackie_fyp.DataClass.Category
import com.example.trackie_fyp.DataClass.Expense
import com.example.trackie_fyp.ReadonlyTextField
import com.example.trackie_fyp.models.CategoryViewModel
import com.example.trackie_fyp.models.ExpenseViewModel
import com.example.trackie_fyp.utils.DateUtils
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TotalAmountScreen(
    totalAmount: Float,
    receiptDate: String,
    userId: Int, // Accept userId as a parameter
    navController: NavHostController,
    expenseViewModel: ExpenseViewModel = viewModel(),
    categoryViewModel: CategoryViewModel = viewModel()
) {
    val context = LocalContext.current
    var extractedText by remember { mutableStateOf("") }

    // Load categories when the composable is first launched
    LaunchedEffect(Unit) {
        Log.d("TotalAmountScreen", "Calling loadCategories()")
        categoryViewModel.loadCategories(userId)
    }

    val categories by categoryViewModel.categories.observeAsState(emptyList())
    // Filter categories to only show those with type "Expense"
    val expenseCategories = categories.filter { it.type == "Expense" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val amount = remember { mutableStateOf(totalAmount.toString()) }
        val description = remember { mutableStateOf(extractedText) }
        var selectedCategory by remember { mutableStateOf<Category?>(null) }

        val dialogState = remember { mutableStateOf(false) }
        val dateState = rememberDatePickerState()
        val textState = remember { mutableStateOf(receiptDate) }

        var amountError by remember { mutableStateOf(false) }
        var categoryError by remember { mutableStateOf(false) }
        var dateError by remember { mutableStateOf(false) }

        ReadonlyTextField(
            value = textState.value,
            onValueChange = {},
            onClick = { dialogState.value = true },
            label = { Text("Date") },
            modifier = Modifier.fillMaxWidth()
        )
        if (dateError) {
            Text(
                text = "Please enter a valid date in the format dd.MM.yyyy",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        if (dialogState.value) {
            DatePickerDialog(
                onDismissRequest = { dialogState.value = false },
                confirmButton = {
                    TextButton(onClick = {
                        val selectedDate = dateState.selectedDateMillis?.let {
                            DateUtils().convertMillisToLocalDate(it)
                        }
                        textState.value = selectedDate?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) ?: ""
                        dialogState.value = false
                        dateError = !isValidDate(textState.value)
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { dialogState.value = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = dateState)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = amount.value,
            onValueChange = {
                amount.value = it
                amountError = it.isBlank() || it.toDoubleOrNull() == null
            },
            label = { Text("Amount") },
            isError = amountError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        if (amountError) {
            Text(
                text = "Please enter a valid amount",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description.value,
            onValueChange = { description.value = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        CategoryDropdownMenu(
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it },
            categories = expenseCategories
        )
        if (categoryError) {
            Text(
                text = "Please select a category",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                amountError = amount.value.isBlank() || amount.value.toDoubleOrNull() == null
                dateError = !isValidDate(textState.value)
                categoryError = selectedCategory == null

                if (!amountError && !categoryError && !dateError) {
                    val budgetId = expenseViewModel.getBudgetIdForCategory(
                        categoryId = selectedCategory?.id ?: 0,
                        month = textState.value.toMonth(),
                        year = textState.value.toYear(),
                        userId = userId
                    )

                    val expense = Expense(
                        id = 0, // SQLite will auto-generate the ID
                        date = textState.value,
                        amount = amount.value.toDouble(),
                        category = selectedCategory,
                        description = description.value,
                        userId = userId,
                        budgetId = budgetId
                    )
                    expenseViewModel.saveExpense(expense, userId)
                    Toast.makeText(context, "Expense saved", Toast.LENGTH_SHORT).show()
                    navController.popBackStack() // Navigate back after saving
                } else {
                    Toast.makeText(context, "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Expense")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                navController.popBackStack("scan", false)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Scanning")
        }
    }
}










