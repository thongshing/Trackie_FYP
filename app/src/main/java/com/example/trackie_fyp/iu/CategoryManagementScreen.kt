package com.example.trackie_fyp.iu

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.trackie_fyp.DataClass.Category
import com.example.trackie_fyp.models.CategoryViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.navigation.NavController


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryManagementScreen(
    navController: NavController,
    userId: Int,
    viewModel: CategoryViewModel = viewModel()
) {
    var categoryName by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("Expense") }
    val categories by viewModel.categories.observeAsState(emptyList())
    var expanded by remember { mutableStateOf(false) }
    var nameError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadCategories(userId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Category Management") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back to Settings")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = categoryName,
                    onValueChange = {
                        categoryName = it
                        nameError = categoryName.isBlank()
                    },
                    label = { Text("Category Name") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = nameError
                )
                if (nameError) {
                    Text(
                        text = "Category name cannot be empty",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = selectedType,
                        onValueChange = { },
                        readOnly = true,
                        label = {},
                        trailingIcon = {
                            Icon(
                                imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown icon",
                                modifier = Modifier.clickable { expanded = !expanded }
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Expense") },
                            onClick = {
                                selectedType = "Expense"
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Income") },
                            onClick = {
                                selectedType = "Income"
                                expanded = false
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        nameError = categoryName.isBlank()
                        if (!nameError) {
                            viewModel.addCategory(categoryName, selectedType, userId)
                            categoryName = ""
                            selectedType = "Expense"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Category")
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(categories) { category ->
                        CategoryItem(
                            category = category,
                            onEdit = { newName, newType ->
                                viewModel.editCategory(category.copy(name = newName, type = newType, userId = userId))
                            },
                            onDelete = {
                                viewModel.deleteCategory(category.id, userId)
                            }
                        )
                        Divider()
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryItem(category: Category, onEdit: (String, String) -> Unit, onDelete: () -> Unit) {
    var isEditing by remember { mutableStateOf(false) }
    var editedName by remember { mutableStateOf(category.name) }
    var editedType by remember { mutableStateOf(category.type) }
    var expanded by remember { mutableStateOf(false) }
    var nameError by remember { mutableStateOf(false) }

    if (isEditing) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = editedName,
                    onValueChange = {
                        editedName = it
                        nameError = editedName.isBlank()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = nameError
                )
                if (nameError) {
                    Text(
                        text = "Category name cannot be empty",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = editedType,
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Type") },
                        trailingIcon = {
                            Icon(
                                imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown icon",
                                modifier = Modifier.clickable { expanded = !expanded }
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Expense") },
                            onClick = {
                                editedType = "Expense"
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Income") },
                            onClick = {
                                editedType = "Income"
                                expanded = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = {
                    nameError = editedName.isBlank()
                    if (!nameError) {
                        onEdit(editedName, editedType)
                        isEditing = false
                    }
                }) {
                    Icon(Icons.Default.Check, contentDescription = "Save")
                }
                IconButton(onClick = { isEditing = false }) {
                    Icon(Icons.Default.Close, contentDescription = "Cancel")
                }
            }
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(category.name, style = MaterialTheme.typography.bodyLarge)
                Text(category.type, style = MaterialTheme.typography.bodySmall)
            }
            Row {
                IconButton(onClick = { isEditing = true }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = { onDelete() }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}