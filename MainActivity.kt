package com.example.ticketbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ticketbook.ui.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    HomeScreen(
                        onGetStartedClick = { navController.navigate("login") }
                    )
                }
                composable("login") {
                    LoginScreen(
                        onLoginClicked = { navController.navigate("mainhome") },
                        onRegisterClicked = { navController.navigate("signup") }
                    )
                }
                composable("signup") {
                    SignUpScreen(
                        onSignUpClicked = { navController.navigate("mainhome") },
                        onLoginTextClicked = { navController.popBackStack("login", inclusive = false) }
                    )
                }
                composable("mainhome") {
                    MainHome(mainNavController = navController) // Pass NavController
                }
            }
        }
    }
}