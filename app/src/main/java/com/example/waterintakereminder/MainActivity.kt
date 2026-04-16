package com.example.waterintakereminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.waterintakereminder.presentation.theme.WaterIntakeReminderTheme
import com.example.waterintakereminder.presentation.navigation.NavGraph
import com.example.waterintakereminder.presentation.navigation.Screen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaterIntakeReminderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WaterIntakeApp()
                }
            }
        }
    }
}

@Composable
fun WaterIntakeApp() {
    val navController = rememberNavController()

    val navItems = listOf(
        BottomNavItem(
            route = Screen.Home.route,
            title = "Home",
            icon = Icons.Default.Home
        ),
        BottomNavItem(
            route = Screen.Stats.route,
            title = "Stats",
            icon = Icons.Default.BarChart
        ),
        BottomNavItem(
            route = Screen.Tips.route,
            title = "Tips",
            icon = Icons.Default.Info
        ),
        BottomNavItem(
            route = Screen.Profile.route,
            title = "Profile",
            icon = Icons.Default.Person
        )
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = Screen.getBottomNavRoutes().contains(currentRoute)
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                navItems.forEach { item ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)