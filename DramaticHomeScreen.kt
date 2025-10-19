package com.example.ticketbook.ui.screens

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.ticketbook.R

@Composable
fun DramaticHomeScreen() {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val insetsController = WindowCompat.getInsetsController(window, view)
            // Make the status bar icons dark, since the background is light
            insetsController.isAppearanceLightStatusBars = true
            // Allow the app to draw behind the system bars
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
    }

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF3E5F5),
            Color(0xFFB3E5FC),
            Color(0xFFD1C4E9)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .systemBarsPadding(), // Add padding to avoid the system bars
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .shadow(8.dp, CircleShape)
                    .background(Color(0xFF5E35B1), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_bus),
                    contentDescription = "Bus",
                    modifier = Modifier.size(48.dp)
                )
            }
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = "Welcome to the future of bus ticketing!",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF283593),
                lineHeight = 32.sp
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Book, manage, and even resell your bus tickets with ease. Your journey starts here.",
                fontSize = 18.sp,
                color = Color(0xFF6A1B9A),
                lineHeight = 26.sp
            )
        }
    }
}