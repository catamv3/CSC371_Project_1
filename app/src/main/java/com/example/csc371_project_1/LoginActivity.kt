package com.example.csc371_project_1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.csc371_project_1.ui.theme.ThemeSwitcher

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Wrap screen in ThemeSwitcher for global persistent dark/light mode
        setContent {
            ThemeSwitcher { _ ->
                LoginScreen(this)
            }
        }
    }
}

@Composable
fun LoginScreen(activity: ComponentActivity) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Login",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(16.dp))

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

        Button(
            onClick = {
                val prefs = activity.getSharedPreferences("user_data", Context.MODE_PRIVATE)
                val storedEmail = prefs.getString("email", null)
                val storedPass = prefs.getString("pass", null)
                val first = prefs.getString("first", "")
                val last = prefs.getString("last", "")
                val dob = prefs.getString("dob", "")

                when {
                    email.isBlank() || pass.isBlank() ->
                        error = "Please enter both email and password"
                    !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                        error = "Invalid email format"
                    email == storedEmail && pass == storedPass -> {
                        val intent = Intent(activity, LandingActivity::class.java).apply {
                            putExtra("first", first)
                            putExtra("last", last)
                            putExtra("dob", dob)
                            putExtra("email", email)
                            putExtra("pass", pass)
                        }
                        activity.startActivity(intent)
                    }
                    else -> error = "Invalid credentials"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        if (error.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
