package com.example.waterintakereminder.presentation.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.waterintakereminder.presentation.ui.screens.home.component.HydrationTipCard

data class HydrationTipModel(
    val id: Int,
    val title: String,
    val message: String,
    val icon: String
)

@Composable
fun HydrationTipsScreen(
    tips: List<HydrationTipModel> = getSampleTips(),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "💧 Hydration Tips & Motivation",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Scroll horizontally to see more tips!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyHorizontalStaggeredGrid(
            rows = StaggeredGridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(
                items = tips,
                key = { tip -> tip.id }
            ) { tip ->
                HydrationTipCard(tip = tip)
            }
        }
    }
}

fun getSampleTips(): List<HydrationTipModel> {
    return listOf(
        HydrationTipModel(
            1,
            "Start Your Day",
            "Drink a glass of water right after waking up to kickstart your metabolism.",
            "🌅"
        ),
        HydrationTipModel(
            2,
            "Before Meals",
            "Drinking water 30 minutes before meals helps with digestion and portion control.",
            "🍽️"
        ),
        HydrationTipModel(
            3,
            "Stay Energized",
            "Even mild dehydration can cause fatigue. Keep a water bottle at your desk!",
            "⚡"
        ),
        HydrationTipModel(
            4,
            "Brain Boost",
            "Water improves concentration and cognitive function. Stay sharp!",
            "🧠"
        ),
        HydrationTipModel(
            5,
            "Skin Health",
            "Proper hydration keeps your skin glowing and reduces wrinkles.",
            "✨"
        ),
        HydrationTipModel(
            6,
            "Track Your Intake",
            "Using this app helps you stay accountable and reach your daily goals!",
            "📱"
        ),
        HydrationTipModel(
            7,
            "Add Flavor",
            "Add lemon, cucumber, or mint to make water more enjoyable.",
            "🍋"
        ),
        HydrationTipModel(
            8,
            "Set Reminders",
            "Regular reminders help build a consistent hydration habit.",
            "⏰"
        ),
        HydrationTipModel(
            9,
            "Post-Workout",
            "Replenish fluids after exercise to aid recovery and performance.",
            "💪"
        ),
        HydrationTipModel(
            10,
            "Listen to Your Body",
            "Thirst is a late sign of dehydration. Drink regularly throughout the day.",
            "👂"
        )
    )
}