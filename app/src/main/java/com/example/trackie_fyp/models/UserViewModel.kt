package com.example.trackie_fyp.models

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int> = _userId

    private val sharedPreferences: SharedPreferences = application.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)

    init {
        val storedUserId = sharedPreferences.getInt("USER_ID", -1)
        Log.d("UserViewModel", "Initializing with stored userId: $storedUserId")
        _userId.value = storedUserId
    }

    fun setUserId(newUserId: Int) {
        _userId.value = newUserId
        sharedPreferences.edit().putInt("USER_ID", newUserId).apply()
        Log.d("UserViewModel", "setUserId: Saved userId: $newUserId in SharedPreferences")
    }

    fun logout() {
        _userId.value = -1
        sharedPreferences.edit().remove("USER_ID").apply()
        sharedPreferences.edit().clear().apply()
        Log.d("UserViewModel", "logout: Cleared SharedPreferences")
    }

    fun clearUserId() {
        _userId.value = -1
        sharedPreferences.edit().remove("USER_ID").apply()
        Log.d("UserViewModel", "clearUserId: Cleared userId in SharedPreferences")
    }
}