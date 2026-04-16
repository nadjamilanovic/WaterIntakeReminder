package com.example.waterintakereminder.presentation.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.waterintakereminder.presentation.view_model.WaterViewModel
import com.example.waterintakereminder.presentation.ui.screens.home.component.StatCard


data class StatCardData(
    val id: Int,
    val title: String,
    val value: String,
    val icon: String,
    val color: androidx.compose.ui.graphics.Color
)

@Composable
fun StatsGridScreen() {
    val viewModel: WaterViewModel = viewModel()
    val totalWater by viewModel.totalWater.collectAsState()
    val waterCount by viewModel.waterCount.collectAsState()
    val averageAmount by viewModel.averageAmount.collectAsState()
    val remainingWater by viewModel.remainingWater.collectAsState()

    val stats = listOf(
        StatCardData(1, "Total Water", "$totalWater ml", "💧", MaterialTheme.colorScheme.primaryContainer),
        StatCardData(2, "Total Drinks", "$waterCount", "🥤", MaterialTheme.colorScheme.secondaryContainer),
        StatCardData(3, "Average", "$averageAmount ml", "📊", MaterialTheme.colorScheme.tertiaryContainer),
        StatCardData(4, "Remaining", "$remainingWater ml", "🎯", MaterialTheme.colorScheme.primaryContainer)
    )

    StatsGridScreenStateless(
        stats = stats
    )
}

@Composable
private fun StatsGridScreenStateless(
    stats: List<StatCardData>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "📊 Statistics Overview",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Your hydration stats at a glance",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = stats,
                key = { stat -> stat.id }
            ) { stat ->
                StatCard(stat = stat)
            }
        }
    }
}
