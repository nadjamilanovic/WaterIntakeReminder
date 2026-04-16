package com.example.waterintakereminder.presentation.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.waterintakereminder.presentation.navigation.Screen
import com.example.waterintakereminder.presentation.view_model.WaterViewModel
import com.example.waterintakereminder.presentation.ui.screens.profile.component.ProfileInfoItem
import com.example.waterintakereminder.presentation.ui.screens.profile.component.AchievementCard

@Composable
fun ProfileScreen(
    navController: NavController
) {
    val viewModel: WaterViewModel = viewModel()
    val intakes by viewModel.intakes.collectAsState()
    val totalWater by viewModel.totalWater.collectAsState()
    val waterCount by viewModel.waterCount.collectAsState()
    val averageAmount by viewModel.averageAmount.collectAsState()

    val totalIntakes = waterCount
    val averagePerDay = averageAmount

    val userName = "Water Warrior"

    val achievements = listOf(
        AchievementModel(1, "7 Day Streak", "7 days of hydration", "🔥"),
        AchievementModel(2, "100 Glasses", "100 glasses of water", "🥤"),
        AchievementModel(3, "Early Bird", "Morning hydration", "🌅"),
        AchievementModel(4, "Hydration Hero", "5000ml in one day", "💪"),
        AchievementModel(5, "2L Master", "Reached daily goal 10x", "🏆")
    )

    ProfileScreenStateless(
        userName = userName,
        dailyGoal = "2000 ml",
        totalIntakes = totalIntakes.toString(),
        averagePerDay = "$averagePerDay ml",
        totalWater = "$totalWater ml",
        achievements = achievements,
        onViewAllAchievements = {
            navController.navigate(Screen.Achievements.route)
        }
    )
}

@Composable
private fun ProfileScreenStateless(
    userName: String,
    dailyGoal: String,
    totalIntakes: String,
    averagePerDay: String,
    totalWater: String,
    achievements: List<AchievementModel>,
    onViewAllAchievements: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "👤",
                    style = MaterialTheme.typography.displayLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = userName,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Water Tracker Enthusiast",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Statistics",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                ProfileInfoItem(title = "Daily Goal", value = dailyGoal)
                Divider(modifier = Modifier.padding(vertical = 4.dp))
                ProfileInfoItem(title = "Total Water Consumed", value = totalWater)
                Divider(modifier = Modifier.padding(vertical = 4.dp))
                ProfileInfoItem(title = "Total Intakes", value = totalIntakes)
                Divider(modifier = Modifier.padding(vertical = 4.dp))
                ProfileInfoItem(title = "Average per Intake", value = averagePerDay)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "🏆 Achievements",
                style = MaterialTheme.typography.titleMedium
            )

            TextButton(onClick = onViewAllAchievements) {
                Text("View All →")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(achievements) { achievement ->
                AchievementCard(
                    title = achievement.title,
                    subtitle = achievement.subtitle,
                    icon = achievement.icon
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Text(
                text = "💪 Keep hydrating! Every glass counts toward your health goal.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}