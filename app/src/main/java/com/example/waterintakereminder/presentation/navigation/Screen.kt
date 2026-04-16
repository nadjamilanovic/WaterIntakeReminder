package com.example.waterintakereminder.presentation.navigation

sealed class Screen(val route: String) {
    data object Register : Screen("register_screen")
    data object Login : Screen("login_screen")

    data object Home : Screen("home_screen")
    data object AllIntakes : Screen("all_intakes_screen")
    data object Detail : Screen("detail_screen")
    data object Settings : Screen("settings_screen")
    data object Profile : Screen("profile_screen")
    data object Achievements : Screen("achievements_screen")
    data object Stats : Screen("stats_screen")
    data object Tips : Screen("tips_screen")
    data object Inspiration : Screen("inspiration_screen")

    data object DetailWithArg : Screen("detail_screen/{intakeId}") {
        fun passId(intakeId: Int): String = "detail_screen/$intakeId"
    }
    companion object {
        fun getBottomNavRoutes(): List<String> = listOf(
            Home.route,
            Stats.route,
            Tips.route,
            Profile.route
        )
    }
}