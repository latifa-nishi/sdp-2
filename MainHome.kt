package com.example.ticketbook.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainHome(mainNavController: NavController) { // Accept the main NavController
    val bottomNavController = rememberNavController()
    Scaffold(
        bottomBar = { MainBottomNavBar(bottomNavController) }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavScreen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavScreen.Home.route) {
                HomeScreen(
                    onGetStartedClick = {
                        bottomNavController.navigate(BottomNavScreen.Search.route)
                    }
                )
            }
            composable(BottomNavScreen.Home.route) { DramaticHomeScreen() }
            composable(BottomNavScreen.Search.route) { SearchBusScreen() }
            composable(BottomNavScreen.MyTickets.route) { MyTicketsScreen() }
            composable(BottomNavScreen.Resale.route) { ResaleTicketScreen() }
            composable(BottomNavScreen.Profile.route) {
                // Pass the sign-out logic to the ProfileScreen
                ProfileScreen(
                    onSignOut = {
                        mainNavController.navigate("login") {
                            // Clear the back stack up to the 'mainhome' route
                            popUpTo("mainhome") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}