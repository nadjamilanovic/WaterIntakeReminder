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
import com.example.waterintakereminder.presentation.ui.screens.profile.util.AchievementModel
import com.example.waterintakereminder.presentation.ui.screens.profile.util.getSampleAchievements
import com.example.waterintakereminder.presentation.ui.components.WaterAppBar

@Composable
fun AchievementsHorizontalGridScreen(
    onNavigateBack: () -> Unit = {}
) {
    val achievements = getSampleAchievements()

    AchievementsHorizontalGridScreenStateless(
        achievements = achievements,
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AchievementsHorizontalGridScreenStateless(
    achievements: List<AchievementModel>,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            WaterAppBar(
                title = "Achievements Gallery",
                showBackButton = true,
                onBackClick = onNavigateBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(vertical = 16.dp)
        ) {
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
}