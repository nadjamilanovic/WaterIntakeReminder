package com.example.waterintakereminder.data.repository

import com.example.waterintakereminder.data.local.entity.TipEntity

interface TipRepository {
    suspend fun getAllTips(): List<TipEntity>
    suspend fun seedTips()
}