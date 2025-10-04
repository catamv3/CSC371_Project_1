package com.example.csc371_project_1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.csc371_project_1.ui.theme.ThemeSwitcher

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Wraps the screen in ThemeSwitcher to apply and persist the selected light/dark theme
        setContent {
            ThemeSwitcher { _ ->
                WelcomeScreen(this)
            }
        }
    }
}

@Composable
fun WelcomeScreen(activity: ComponentActivity) {
    // Main layout container with padding and centered content
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Arranges components vertically in the center of the screen
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Displays welcome text at the top
            Text(
                "Welcome!",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(32.dp))

            // Button that navigates to the RegisterActivity
            Button(
                onClick = {
                    val intent = Intent(activity, RegisterActivity::class.java)
                    activity.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register")
            }

            Spacer(Modifier.height(16.dp))

            // Outlined button that navigates to the LoginActivity
            OutlinedButton(
                onClick = {
                    val intent = Intent(activity, LoginActivity::class.java)
                    activity.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }
        }
    }
}
