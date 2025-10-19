package com.example.ticketbook.ui.screens

import androidx.annotation.DrawableRes
import com.example.ticketbook.R

sealed class BottomNavScreen(
    val route: String,
    val label: String,
    @DrawableRes val icon: Int
) {
    object Home : BottomNavScreen("home", "Home", R.drawable.ic_home)
    object Search : BottomNavScreen("search", "Search", R.drawable.ic_search)
    object MyTickets : BottomNavScreen("mytickets", "My Tickets", R.drawable.ic_ticket)
    object Resale : BottomNavScreen("resale", "Resale", R.drawable.ic_resale)
    object Profile : BottomNavScreen("profile", "Profile", R.drawable.ic_profile)
}