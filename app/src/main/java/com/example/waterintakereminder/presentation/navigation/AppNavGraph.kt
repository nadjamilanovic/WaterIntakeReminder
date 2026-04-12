package com.example.waterintakereminder.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.waterintakereminder.ui.screens.*

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(navController = navController)
        }

        composable("list") {
            ListScreen(
                onItemClick = { amount ->
                    navController.navigate("detail/$amount")
                }
            )
        }

        composable(
            route = "detail/{amount}",
            arguments = listOf(
                navArgument("amount") { type = NavType.IntType }
            )
        ) { backStackEntry ->

            val amount = backStackEntry.arguments?.getInt("amount") ?: 0

            DetailScreen(amount = amount)
        }

        composable("settings") {
            SettingsScreen()
        }
    }
}