package com.example.waterintakereminder.presentation.ui.screens.home.util

data class HydrationTipModel(
    val id: Int,
    val title: String,
    val message: String,
    val icon: String
)

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