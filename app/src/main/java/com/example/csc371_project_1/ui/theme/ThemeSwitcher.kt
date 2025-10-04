package com.example.csc371_project_1.ui.theme

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun ThemeSwitcher(content: @Composable (isDarkMode: Boolean) -> Unit) {
    val context = LocalContext.current

    // ✅ Load saved theme from SharedPreferences
    var isDarkMode by remember {
        mutableStateOf(loadThemePreference(context))
    }

    CSC371_Project_1Theme(darkTheme = isDarkMode) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Main screen content
            content(isDarkMode)

            // 🌗 Toggle icon in bottom-left corner
            IconButton(
                onClick = {
                    isDarkMode = !isDarkMode
                    saveThemePreference(context, isDarkMode)
                },
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                if (isDarkMode)
                    Icon(
                        Icons.Filled.Brightness7,
                        contentDescription = "Switch to light mode",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                else
                    Icon(
                        Icons.Filled.Brightness4,
                        contentDescription = "Switch to dark mode",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
            }
        }
    }
}

// ✅ Helper functions to persist theme preference
private fun saveThemePreference(context: Context, isDarkMode: Boolean) {
    val prefs = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
    prefs.edit().putBoolean("dark_mode", isDarkMode).apply()
}

private fun loadThemePreference(context: Context): Boolean {
    val prefs = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
    return prefs.getBoolean("dark_mode", false)
}
