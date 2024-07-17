package com.example.trackie_fyp.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.trackie_fyp.DatabaseHelper
import com.example.trackie_fyp.DataClass.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = DatabaseHelper(application)
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _categories.postValue(dbHelper.getAllCategories())
        }
    }

    fun addCategory(name: String, type: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.addCategory(Category(name = name, type = type))
            loadCategories()
        }
    }

    fun editCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.updateCategory(category)
            loadCategories()
        }
    }

    fun deleteCategory(categoryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.deleteCategory(categoryId)
            loadCategories()
        }
    }
}