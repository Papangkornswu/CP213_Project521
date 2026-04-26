package com.talkdeck.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.talkdeck.screens.GameScreen
import com.talkdeck.screens.HomeScreen
import com.talkdeck.screens.SetupScreen
import com.talkdeck.viewmodel.GameViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // Shared ViewModel across screens
    val gameViewModel: GameViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onStartGame = { navController.navigate("setup") }
            )
        }
        composable("setup") {
            SetupScreen(
                viewModel = gameViewModel,
                onGameStarted = { navController.navigate("game") },
                onBack = { navController.popBackStack() }
            )
        }
        composable("game") {
            GameScreen(
                viewModel = gameViewModel,
                onEndGame = { 
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = false }
                    }
                }
            )
        }
    }
}
