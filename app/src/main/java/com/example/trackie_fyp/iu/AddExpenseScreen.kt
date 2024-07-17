package com.example.trackie_fyp.iu

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.trackie_fyp.*
import com.example.trackie_fyp.DataClass.Category
import com.example.trackie_fyp.DataClass.Expense
import com.example.trackie_fyp.models.CategoryViewModel
import com.example.trackie_fyp.models.ExpenseViewModel
import com.example.trackie_fyp.ui.theme.Trackie_FYPTheme
import com.example.trackie_fyp.utils.DateUtils
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    navController: NavHostController,
    expenseId: Int? = null,
    expenseViewModel: ExpenseViewModel = viewModel(),
    categoryViewModel: CategoryViewModel = viewModel()
) {
    val context = LocalContext.current
    var extractedText by remember { mutableStateOf("") }

    // Load categories when the composable is first launched
    LaunchedEffect(Unit) {
        Log.d("AddExpenseScreen", "Calling loadCategories()")
        categoryViewModel.loadCategories()
    }

    val expense by expenseViewModel.getExpenseById(expenseId ?: -1).observeAsState()
    val amount = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    val categories by categoryViewModel.categories.observeAsState(emptyList())
    val dateState = rememberDatePickerState()
    val textState = remember { mutableStateOf("") }

    // Update states when the expense is loaded
    LaunchedEffect(expense) {
        expense?.let {
            amount.value = it.amount.toString()
            description.value = it.description
            selectedCategory = it.category
            textState.value = it.date
            dateState.selectedDateMillis = it.date.toDate().time
        }
    }

    // Filter categories to only show those with type "Expense"
    val expenseCategories = categories.filter { it.type == "Expense" }

    // Debug log to verify categories are loaded
    LaunchedEffect(categories) {
        Log.d("AddExpenseScreen", "Categories loaded: ${categories.size}")
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
            onClick = { dialogState.value = true },
            label = { Text("Date") },
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

        TextField(
            value = amount.value,
            onValueChange = { amount.value = it },
            label = { Text("Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
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

        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                val newExpense = Expense(
                    id = expense?.id ?: 0, // Use existing ID if editing, otherwise auto-generate
                    date = textState.value,
                    amount = amount.value.toDouble(),
                    category = selectedCategory,
                    description = description.value,
                    receiptImage = null // Handle receipt image logic if needed
                )
                if (expense != null) {
                    expenseViewModel.updateExpense(newExpense)
                } else {
                    expenseViewModel.saveExpense(newExpense)
                }
                Toast.makeText(context, "Expense saved", Toast.LENGTH_SHORT).show()
                navController.popBackStack() // Navigate back after saving
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Expense")
        }
    }
}

@Composable
fun CategoryDropdownMenu(
    selectedCategory: Category?,
    onCategorySelected: (Category) -> Unit,
    categories: List<Category>
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(selectedCategory?.name ?: "Select Category") }

    LaunchedEffect(selectedCategory) {
        selectedText = selectedCategory?.name ?: "Select Category"
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                expanded = true
                Log.d("CategoryDropdownMenu", "Box clicked, expanded = $expanded")
            }
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Text(
            text = selectedText,
            style = MaterialTheme.typography.bodyLarge,
            color = if (selectedText == "Select Category") MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurface
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                Log.d("CategoryDropdownMenu", "Dropdown dismissed, expanded = $expanded")
            }
        ) {
            Log.d("CategoryDropdownMenu", "Rendering categories: ${categories.size}")
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.name) },
                    onClick = {
                        onCategorySelected(category)
                        selectedText = category.name
                        expanded = false
                        Log.d("CategoryDropdownMenu", "Category selected: ${category.name}, expanded = $expanded")
                    }
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun AddExpenseScreenPreview() {
    Trackie_FYPTheme {
        AddExpenseScreen(navController = rememberNavController())
    }
}