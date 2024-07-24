package com.example.trackie_fyp.models

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackie_fyp.DatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.MessageDigest

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val dbHelper = DatabaseHelper.getInstance(application.applicationContext)
    private val sharedPreferences: SharedPreferences = application.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)

    fun login(username: String, password: String, onResult: (Int, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val hashedPassword = hashPassword(password)
                val userExists = dbHelper.loginUser(username, hashedPassword)
                if (userExists) {
                    val userId = dbHelper.getUserId(username)
                    Log.d("LoginViewModel", "Login successful, userId: $userId")
                    saveLoginState(userId)
                    Toast.makeText(getApplication(), "Login Successful", Toast.LENGTH_SHORT).show()
                    onResult(userId, null)
                } else {
                    Log.d("LoginViewModel", "Login failed for username: $username")
                    Toast.makeText(getApplication(), "Invalid Credentials", Toast.LENGTH_SHORT).show()
                    onResult(-1, "Invalid Credentials")
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Login error: ${e.message}", e)
                Toast.makeText(getApplication(), "An error occurred. Please try again.", Toast.LENGTH_SHORT).show()
                onResult(-1, "An error occurred. Please try again.")
            }
        }
    }

    private fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private fun saveLoginState(userId: Int) {
        sharedPreferences.edit().putInt("USER_ID", userId).apply()
        Log.d("LoginViewModel", "Saved userId: $userId in SharedPreferences")
    }

    fun register(username: String, password: String, onSuccess: (Int) -> Unit) {
        viewModelScope.launch {
            if (dbHelper.getUserId(username) != -1) {
                Log.d("LoginViewModel", "Username already exists: $username")
                Toast.makeText(getApplication(), "Username already exists. Please choose a different username.", Toast.LENGTH_SHORT).show()
                onSuccess(-1)
                return@launch
            }

            if (password.length < 8 || !password.matches(".*[!@#\$%^&*].*".toRegex())) {
                Log.d("LoginViewModel", "Password does not meet security requirements")
                Toast.makeText(getApplication(), "Password must be at least 8 characters long and contain at least one special character.", Toast.LENGTH_SHORT).show()
                onSuccess(-1)
                return@launch
            }

            val hashedPassword = hashPassword(password)
            val result = dbHelper.registerUser(username, hashedPassword)
            if (result != -1L) {
                val userId = dbHelper.getUserId(username)
                Log.d("LoginViewModel", "Registration successful, userId: $userId")
                saveLoginState(userId)
                Toast.makeText(getApplication(), "Registration Successful", Toast.LENGTH_SHORT).show()
                onSuccess(userId)
            } else {
                Log.d("LoginViewModel", "Registration failed for username: $username")
                Toast.makeText(getApplication(), "Registration Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}