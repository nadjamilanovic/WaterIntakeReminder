package com.example.waterintakereminder.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.waterintakereminder.presentation.ui.screens.home.HomeScreen
import com.example.waterintakereminder.presentation.ui.screens.list.ListScreen
import com.example.waterintakereminder.presentation.ui.screens.detail.DetailScreen
import com.example.waterintakereminder.presentation.ui.screens.settings.SettingsScreen
import com.example.waterintakereminder.presentation.ui.screens.profile.ProfileScreen
import com.example.waterintakereminder.presentation.ui.screens.profile.AchievementsHorizontalGridScreen
import com.example.waterintakereminder.presentation.ui.screens.home.StatsGridScreen
import com.example.waterintakereminder.presentation.ui.screens.home.HydrationTipsScreen
import com.example.waterintakereminder.presentation.ui.screens.home.HydrationInspirationScreen



@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Home.route,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.Register.route) {
            Text("Register Screen - to be implemented")
        }

        composable(route = Screen.Login.route) {
            Text("Login Screen - to be implemented")
        }

        composable(route = Screen.Home.route) {
            HomeScreen(
                navController = navController,
                onNavigateToList = { navController.navigate(Screen.AllIntakes.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                onNavigateToProfile = {   navController.navigate(Screen.Profile.route) {
                    launchSingleTop = true
                } },
                onNavigateToTips = { navController.navigate(Screen.Tips.route) },
                onNavigateToInspiration = { navController.navigate(Screen.Inspiration.route) }
                )
        }

        composable(route = Screen.AllIntakes.route) {
            ListScreen(
                onItemClick = { amount ->
                    navController.navigate(Screen.DetailWithArg.passId(amount))
                }
            )
        }

        composable(
            route = Screen.DetailWithArg.route,
            arguments = listOf(
                navArgument("intakeId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val intakeId = backStackEntry.arguments?.getInt("intakeId") ?: 0
            DetailScreen(
                intakeId = intakeId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(route = Screen.Settings.route) {
            SettingsScreen()
        }

        composable(route = Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(route = Screen.Achievements.route) {
            AchievementsHorizontalGridScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(route = Screen.Stats.route) {
            StatsGridScreen()
        }
        composable(route = Screen.Tips.route) {
            HydrationTipsScreen()
        }
        composable(route = Screen.Inspiration.route) {
            HydrationInspirationScreen()
        }
    }
}