package com.example.trackie_fyp.ui.theme


import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

object AppThemeSwitcher {
    private lateinit var sharedPreferences: SharedPreferences

    private val _isDarkMode = mutableStateOf(false)
    val isDarkMode: State<Boolean> = _isDarkMode

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        _isDarkMode.value = sharedPreferences.getBoolean("DARK_MODE", false)
    }

    fun switch(isDark: Boolean) {
        _isDarkMode.value = isDark
        sharedPreferences.edit().putBoolean("DARK_MODE", isDark).apply()
    }
}