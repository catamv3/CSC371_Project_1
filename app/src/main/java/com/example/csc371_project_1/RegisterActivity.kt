package com.example.csc371_project_1

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.csc371_project_1.ui.theme.ThemeSwitcher

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Wraps this screen in ThemeSwitcher to apply the user's selected theme mode
        setContent {
            ThemeSwitcher { _ ->
                RegisterScreen(this)
            }
        }
    }
}

@Composable
fun RegisterScreen(activity: ComponentActivity) {
    // Accesses the current context for saving user data and showing messages
    val ctx = LocalContext.current

    // State variables for storing user input and error messages
    var first by remember { mutableStateOf("") }
    var last by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    // Layout for registration fields and submit button
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Screen title
        Text(
            "Register",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(16.dp))

        // Input fields for user data
        OutlinedTextField(
            value = first,
            onValueChange = { first = it },
            label = { Text("First name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = last,
            onValueChange = { last = it },
            label = { Text("Family name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = dob,
            onValueChange = { dob = it },
            label = { Text("Date of birth") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // Button to validate inputs and save data if successful
        Button(
            onClick = {
                when {
                    // Validation checks for empty or incorrect fields
                    first.isBlank() || last.isBlank() || dob.isBlank() || email.isBlank() || pass.isBlank() ->
                        error = "All fields are required"
                    first.length < 3 || first.length > 30 ->
                        error = "First name must be between 3 and 30 characters"
                    !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                        error = "Invalid email format"
                    pass.length < 6 ->
                        error = "Password must be at least 6 characters"
                    else -> {
                        // Save user data to SharedPreferences
                        val prefs = ctx.getSharedPreferences("user_data", Context.MODE_PRIVATE)
                        with(prefs.edit()) {
                            putString("first", first)
                            putString("last", last)
                            putString("dob", dob)
                            putString("email", email)
                            putString("pass", pass)
                            apply()
                        }

                        // Show success message and return to main screen
                        Toast.makeText(ctx, "Registration successful!", Toast.LENGTH_SHORT).show()
                        activity.finish()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Account")
        }

        // Display error messages if validation fails
        if (error.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Text(
                error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
