package com.example.waterintakereminder.presentation.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.waterintakereminder.presentation.ui.screens.home.component.InspirationCard

data class InspirationModel(
    val id: Int,
    val title: String,
    val message: String,
    val author: String,
    val icon: String
)

@Composable
fun HydrationInspirationScreen(
    cards: List<InspirationModel> = getSampleInspirations(),
    modifier: Modifier = Modifier
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        contentPadding = PaddingValues(16.dp),
        verticalItemSpacing = 16.dp,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TitleSection()
        }

        items(
            items = cards,
            key = { card -> card.id }
        ) { card ->
            InspirationCard(inspiration = card)
        }
    }
}

@Composable
private fun TitleSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Text(
            text = "💭 Hydration Inspiration",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Words of wisdom to keep you hydrated",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

fun getSampleInspirations(): List<InspirationModel> {
    return listOf(
        InspirationModel(
            1,
            "Water is Life",
            "Water is the driving force of all nature. Every cell in your body needs it to function properly.",
            "Leonardo da Vinci",
            "💧"
        ),
        InspirationModel(
            2,
            "Small Steps",
            "Every glass of water is a step toward better health. Small consistent actions create big results.",
            "Anonymous",
            "🥤"
        ),
        InspirationModel(
            3,
            "Listen to Your Body",
            "Your body speaks to you through thirst. Listen to it. Respect it. Hydrate it.",
            "Wellness Wisdom",
            "👂"
        ),
        InspirationModel(
            4,
            "Pure Energy",
            "Water is nature's energy drink. No calories, no sugar, just pure hydration.",
            "Health Expert",
            "⚡"
        ),
        InspirationModel(
            5,
            "Mindful Hydration",
            "Drink water mindfully. Feel it nourish every part of your being.",
            "Mindfulness Guide",
            "🧘"
        ),
        InspirationModel(
            6,
            "Glowing Skin",
            "Hydration is the secret to glowing skin. Drink your way to radiance.",
            "Beauty Tip",
            "✨"
        ),
        InspirationModel(
            7,
            "Morning Ritual",
            "Start each day with a glass of water. It's a promise you make to your health.",
            "Daily Habit",
            "🌅"
        ),
        InspirationModel(
            8,
            "Stay Sharp",
            "Your brain is 73% water. Keep it hydrated to stay focused and sharp.",
            "Neuroscience",
            "🧠"
        ),
        InspirationModel(
            9,
            "You've Got This",
            "Reaching your water goal is a victory. Celebrate every glass!",
            "Motivation",
            "🏆"
        ),
        InspirationModel(
            10,
            "Nature's Gift",
            "Water is the most natural medicine. Drink freely, live fully.",
            "Natural Healing",
            "🌊"
        ),
        InspirationModel(
            11,
            "Consistency",
            "One glass at a time. That's how habits are built. That's how health is maintained.",
            "Habit Coach",
            "📅"
        ),
        InspirationModel(
            12,
            "Self-Care",
            "Drinking water is an act of self-love. You deserve to feel your best.",
            "Self-Care Daily",
            "💕"
        )
    )
}