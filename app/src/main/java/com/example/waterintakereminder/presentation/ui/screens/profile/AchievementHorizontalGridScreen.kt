package com.example.waterintakereminder.presentation.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.waterintakereminder.presentation.ui.screens.profile.component.AchievementCard
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment

data class AchievementModel(
    val id: Int,
    val title: String,
    val subtitle: String,
    val icon: String
)

@Composable
fun AchievementsHorizontalGridScreen(
    achievements: List<AchievementModel> = getSampleAchievements(),
    onNavigateBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "🏆 Achievements Gallery",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .padding(top = 16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = achievements,
                key = { achievement -> achievement.id }
            ) { achievement ->
                AchievementCard(
                    title = achievement.title,
                    subtitle = achievement.subtitle,
                    icon = achievement.icon
                )
            }
        }
    }
}

fun getSampleAchievements(): List<AchievementModel> {
    return listOf(
        AchievementModel(1, "7 Day Streak", "7 days of hydration", "🔥"),
        AchievementModel(2, "100 Glasses", "100 glasses of water", "🥤"),
        AchievementModel(3, "Early Bird", "Morning hydration", "🌅"),
        AchievementModel(4, "Hydration Hero", "5000ml in one day", "💪"),
        AchievementModel(5, "2L Master", "Reached daily goal 10x", "🏆"),
        AchievementModel(6, "Perfect Week", "7/7 days met goal", "⭐"),
        AchievementModel(7, "Night Owl", "Late night hydration", "🦉"),
        AchievementModel(8, "Consistency King", "30 day streak", "👑")
    )
}