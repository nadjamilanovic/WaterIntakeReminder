package com.example.waterintakereminder.presentation.navigation.bottom_nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import com.example.waterintakereminder.presentation.navigation.Screen


object BottomNavItems {
    val items = listOf(
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
}