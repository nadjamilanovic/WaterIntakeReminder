package com.example.waterintakereminder.data.datasource.network.dto

import com.google.gson.annotations.SerializedName

data class WaterIntakeDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("amount_ml")
    val amountMl: Int,
    @SerializedName("timestamp")
    val timestamp: String,
    @SerializedName("is_drank")
    val isDrank: Boolean,
    @SerializedName("user_id")
    val userId: Int
)