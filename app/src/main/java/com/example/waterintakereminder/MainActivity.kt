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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.waterintakereminder.presentation.theme.WaterIntakeReminderTheme
import com.example.waterintakereminder.presentation.navigation.NavGraph
import com.example.waterintakereminder.presentation.navigation.Screen
import com.example.waterintakereminder.presentation.navigation.bottom_nav.BottomNavItems
import com.example.waterintakereminder.data.model.WaterIntake
import com.example.waterintakereminder.presentation.ui.screens.home.util.getSampleIntakes

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

    var intakes by remember { mutableStateOf<List<WaterIntake>>(emptyList()) }

    LaunchedEffect(Unit) {
        intakes = getSampleIntakes()
    }

    val navItems = BottomNavItems.items

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = Screen.getBottomNavRoutes().contains(currentRoute)

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    navItems.forEach { item ->
                        NavigationBarItem(
                            selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(Screen.Home.route) {
                                        inclusive = false
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
        }
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues),
            intakes = intakes,
            onUpdateIntakes = { intakes = it }
        )
    }
}