package com.example.csc371_project_1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.csc371_project_1.ui.theme.ThemeSwitcher

class LandingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Retrieve user data
        val first = intent.getStringExtra("first") ?: ""
        val last = intent.getStringExtra("last") ?: ""
        val dob = intent.getStringExtra("dob") ?: ""
        val email = intent.getStringExtra("email") ?: ""
        val pass = intent.getStringExtra("pass") ?: ""

        // Apply persistent theme via ThemeSwitcher
        setContent {
            ThemeSwitcher { _ ->
                LandingScreen(
                    first = first,
                    last = last,
                    dob = dob,
                    email = email,
                    pass = pass,
                    onReturnHome = {
                        val intent = Intent(this, MainActivity::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        }
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun LandingScreen(
    first: String,
    last: String,
    dob: String,
    email: String,
    pass: String,
    onReturnHome: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            // Profile Section
            Spacer(Modifier.height(20.dp))
            Box(contentAlignment = Alignment.Center) {
                // Replace with a real image if you want
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
            }

            Text(
                text = "$first $last",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Professional Portfolio",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )

            //Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        "Personal Information",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))

                    InfoRow("Full Name", "$first $last")
                    InfoRow("Date of Birth", dob)
                    InfoRow("Email", email)
                    InfoRow("Password", pass) // ⚠️ Demo only — remove for real apps
                }
            }

            // 🏠 Return Button
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onReturnHome,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Return to Main Menu", fontSize = 16.sp)
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}

// Helper Composable for uniform info display
@Composable
fun InfoRow(label: String, value: String) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
        )
        Text(
            text = value.ifBlank { "—" },
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
        Divider(
            modifier = Modifier
                .padding(vertical = 6.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
        )
    }
}
