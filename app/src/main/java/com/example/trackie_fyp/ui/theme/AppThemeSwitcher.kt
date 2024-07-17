package com.example.trackie_fyp.ui.theme


import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

object AppThemeSwitcher {
    private val _isDarkMode = mutableStateOf(false)
    val isDarkMode: State<Boolean> = _isDarkMode

    fun switch(isDark: Boolean) {
        _isDarkMode.value = isDark
    }
}