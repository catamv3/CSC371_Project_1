package com.example.csc371_project_1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Displays the splash screen first when the app launches
        setContent {
            SplashScreen {
                // After the delay, navigate to the main screen
                startActivity(Intent(this, MainActivity::class.java))
                finish() // Closes splash screen so it cannot be returned to
            }
        }
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    // Runs a coroutine that waits 2 seconds before triggering navigation
    LaunchedEffect(Unit) {
        delay(2000)
        onTimeout()
    }

    // Layout for the splash screen content
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Displays an image or logo
            Image(
                painter = painterResource(R.drawable.fsclogo),
                contentDescription = "Whitman Hall",
                modifier = Modifier.size(200.dp)
            )
            Spacer(Modifier.height(16.dp))
            // Displays project title text
            Text(
                "CSC371 Project 1",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}
