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
import com.example.waterintakereminder.data.model.WaterIntake
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.waterintakereminder.presentation.ui.screens.home.CloudHomeScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Home.route,
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
                viewModel = hiltViewModel(),
                navController = navController
            )
        }

        composable(route = Screen.AllIntakes.route) {
            ListScreen(
                viewModel = hiltViewModel(),
                onNavigateToDetail = { amount ->
                    navController.navigate(Screen.DetailWithArg.passData(WaterIntake(0, amount, "", false)))
                }
            )
        }

        composable(
            route = Screen.DetailWithArg.route,
            arguments = listOf(
                navArgument("amountMl") { type = NavType.IntType },
                navArgument("time") { type = NavType.StringType },
                navArgument("isDrank") { type = NavType.BoolType },
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val amountMl = backStackEntry.arguments?.getInt("amountMl") ?: 0
            val time = backStackEntry.arguments?.getString("time") ?: ""
            val isDrank = backStackEntry.arguments?.getBoolean("isDrank") ?: false
            val id = backStackEntry.arguments?.getInt("id") ?: 0

            val intake = WaterIntake(
                id = id,
                amountMl = amountMl,
                time = time,
                isDrank = isDrank
            )

            DetailScreen(
                intake = intake,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(route = Screen.Settings.route) {
            SettingsScreen(
                viewModel = hiltViewModel()
            )
        }

        composable(route = Screen.Profile.route) {
            ProfileScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
        composable(route = Screen.Achievements.route) {
            AchievementsHorizontalGridScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(route = Screen.Stats.route) {
            StatsGridScreen(
                viewModel = hiltViewModel()
            )
        }

        composable(route = Screen.Tips.route) {
            HydrationTipsScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }

        composable(route = Screen.Inspiration.route) {
            HydrationInspirationScreen()
        }
        composable(route = "cloud_home/{userId}") {
            CloudHomeScreen(
                userId = it.arguments?.getString("userId") ?: "",
                navController = navController
            )
        }
    }
}