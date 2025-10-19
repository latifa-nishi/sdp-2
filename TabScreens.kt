package com.example.ticketbook.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier



@Composable
fun MyTicketsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Your previously purchased tickets will be shown here.",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
fun ResaleTicketScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Sell or buy previously purchased tickets.",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}