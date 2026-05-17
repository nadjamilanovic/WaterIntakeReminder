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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.waterintakereminder.presentation.ui.screens.home.component.StatCard
import com.example.waterintakereminder.presentation.view_model.stats.StatsViewModel
import com.example.waterintakereminder.presentation.view_model.stats.StatsUiState
import com.example.waterintakereminder.presentation.ui.screens.home.util.StatCardData

@Composable
fun StatsGridScreen(
    viewModel: StatsViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is StatsUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is StatsUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Error: ${(uiState as StatsUiState.Error).message}")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.resetUiState() }) {
                        Text("Retry")
                    }
                }
            }
        }
        is StatsUiState.Success -> {
            val statsData = (uiState as StatsUiState.Success).statsData

            val stats = listOf(
                StatCardData(1, "Total Water", "${statsData.totalWater} ml", "💧", MaterialTheme.colorScheme.primaryContainer),
                StatCardData(2, "Total Drinks", "${statsData.totalDrinks}", "🥤", MaterialTheme.colorScheme.secondaryContainer),
                StatCardData(3, "Average", "${statsData.averageAmount} ml", "📊", MaterialTheme.colorScheme.tertiaryContainer),
                StatCardData(4, "Remaining", "${statsData.remainingWater} ml", "🎯", MaterialTheme.colorScheme.primaryContainer)
            )

            StatsGridScreenStateless(stats = stats)
        }
        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Initializing...")
            }
        }
    }
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