package com.example.waterintakereminder.presentation.ui.screens.home.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class StatCardData(
    val id: Int,
    val title: String,
    val value: String,
    val icon: String,
    val color: Color
)

@Composable
fun getSampleStats(): List<StatCardData> {
    return listOf(
        StatCardData(1, "Total Water", "1250 ml", "💧", MaterialTheme.colorScheme.primaryContainer),
        StatCardData(2, "Total Drinks", "5", "🥤", MaterialTheme.colorScheme.secondaryContainer),
        StatCardData(3, "Average", "250 ml", "📊", MaterialTheme.colorScheme.tertiaryContainer),
        StatCardData(4, "Remaining", "750 ml", "🎯", MaterialTheme.colorScheme.primaryContainer)
    )
}