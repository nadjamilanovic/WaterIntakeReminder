package com.example.waterintakereminder.presentation.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.waterintakereminder.presentation.navigation.Screen
import com.example.waterintakereminder.presentation.ui.screens.profile.component.ProfileInfoItem
import com.example.waterintakereminder.presentation.ui.screens.profile.component.AchievementCard
import com.example.waterintakereminder.presentation.ui.screens.profile.util.AchievementModel
import com.example.waterintakereminder.presentation.ui.screens.profile.util.getProfileIntakes
import com.example.waterintakereminder.presentation.ui.screens.profile.util.getProfileAchievements
import com.example.waterintakereminder.presentation.ui.components.WaterAppBar

@Composable
fun ProfileScreen(
    navController: NavController
) {
    val intakes = getProfileIntakes()

    val totalWater = intakes.sumOf { it.amountMl }
    val waterCount = intakes.size
    val averageAmount = if (intakes.isNotEmpty()) intakes.sumOf { it.amountMl } / intakes.size else 0

    val userName = "Water Warrior"

    val achievements = getProfileAchievements()

    ProfileScreenStateless(
        userName = userName,
        dailyGoal = "2000 ml",
        totalIntakes = waterCount.toString(),
        averagePerDay = "$averageAmount ml",
        totalWater = "$totalWater ml",
        achievements = achievements,
        onViewAllAchievements = {
            navController.navigate(Screen.Achievements.route)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
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
    Scaffold(
        topBar = {
            WaterAppBar(title = "Profile")
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                    ProfileInfoItem(title = "Total Water Consumed", value = totalWater)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                    ProfileInfoItem(title = "Total Intakes", value = totalIntakes)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
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
}