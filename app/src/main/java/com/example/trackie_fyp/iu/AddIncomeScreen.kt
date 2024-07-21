package com.example.trackie_fyp.iu

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trackie_fyp.DataClass.Category
import com.example.trackie_fyp.DataClass.Income
import com.example.trackie_fyp.ReadonlyTextField
import com.example.trackie_fyp.models.CategoryViewModel
import com.example.trackie_fyp.models.ExpenseViewModel
import com.example.trackie_fyp.utils.DateUtils
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIncomeScreen(
    navController: NavHostController,
    userId: Int, // Add this parameter
    incomeId: Int? = null, // Add this parameter
    expenseViewModel: ExpenseViewModel = viewModel(),
    categoryViewModel: CategoryViewModel = viewModel()
) {
    val context = LocalContext.current

    // Load categories when the composable is first launched
    LaunchedEffect(Unit) {
        Log.d("AddIncomeScreen", "Calling loadCategories()")
        categoryViewModel.loadCategories(userId)
    }

    val income by expenseViewModel.getIncomeById(incomeId ?: -1).observeAsState()
    val amount = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    val categories by categoryViewModel.categories.observeAsState(emptyList())
    val dateState = rememberDatePickerState()
    val textState = remember { mutableStateOf("") }

    // Update states when the income is loaded
    LaunchedEffect(income) {
        income?.let {
            amount.value = it.amount.toString()
            description.value = it.description
            selectedCategory = it.category
            textState.value = it.date
            dateState.selectedDateMillis = it.date.toDate()?.time
        }
    }

    // Filter categories to only show those with type "Income"
    val incomeCategories = categories.filter { it.type == "Income" }

    // Debug log to verify categories are loaded
    LaunchedEffect(categories) {
        Log.d("AddIncomeScreen", "Categories loaded: ${categories.size}")
    }

    val dialogState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ReadonlyTextField(
            value = textState.value,
            onValueChange = {},
            label = { Text("Date") },
            onClick = { dialogState.value = true },
            modifier = Modifier.fillMaxWidth()
        )

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
            onValueChange = { amount.value = it },
            label = { Text("Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

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
            categories = incomeCategories,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                val newIncome = Income(
                    id = income?.id ?: 0, // Use existing ID if editing, otherwise auto-generate
                    date = textState.value,
                    amount = amount.value.toDouble(),
                    category = selectedCategory,
                    description = description.value,
                    userId = userId // Ensure userId is passed when saving the income
                )
                if (income != null) {
                    expenseViewModel.updateIncome(newIncome, userId)
                } else {
                    expenseViewModel.saveIncome(newIncome, userId)
                }
                Toast.makeText(context, "Income saved", Toast.LENGTH_SHORT).show()
                navController.popBackStack() // Navigate back after saving
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Income")
        }
    }
}


