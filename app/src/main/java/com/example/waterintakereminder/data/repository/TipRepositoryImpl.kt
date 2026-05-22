package com.example.waterintakereminder.data.repository

import com.example.waterintakereminder.data.local.dao.TipDao
import com.example.waterintakereminder.data.local.entity.TipEntity
import javax.inject.Inject

class TipRepositoryImpl @Inject constructor(
    private val tipDao: TipDao
) : TipRepository {

    override suspend fun getAllTips(): List<TipEntity> {
        return tipDao.getAllTips()
    }

    override suspend fun seedTips() {
        if (tipDao.getAllTips().isEmpty()) {
            val sampleTips = listOf(
                TipEntity(title = "Start Your Day", message = "Drink a glass of water right after waking up to kickstart your metabolism.", icon = "🌅"),
                TipEntity(title = "Before Meals", message = "Drinking water 30 minutes before meals helps with digestion and portion control.", icon = "🍽️"),
                TipEntity(title = "Stay Energized", message = "Even mild dehydration can cause fatigue. Keep a water bottle at your desk!", icon = "⚡"),
                TipEntity(title = "Brain Boost", message = "Water improves concentration and cognitive function. Stay sharp!", icon = "🧠"),
                TipEntity(title = "Skin Health", message = "Proper hydration keeps your skin glowing and reduces wrinkles.", icon = "✨"),
                TipEntity(title = "Track Your Intake", message = "Using this app helps you stay accountable and reach your daily goals!", icon = "📱"),
                TipEntity(title = "Add Flavor", message = "Add lemon, cucumber, or mint to make water more enjoyable.", icon = "🍋"),
                TipEntity(title = "Set Reminders", message = "Regular reminders help build a consistent hydration habit.", icon = "⏰"),
                TipEntity(title = "Post-Workout", message = "Replenish fluids after exercise to aid recovery and performance.", icon = "💪"),
                TipEntity(title = "Listen to Your Body", message = "Thirst is a late sign of dehydration. Drink regularly throughout the day.", icon = "👂")
            )
            tipDao.insertTips(sampleTips)
        }
    }
}