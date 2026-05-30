package com.example.waterintakereminder.data.datasource.network.dto

import com.google.gson.annotations.SerializedName

data class CreateWaterIntakeDto(
    @SerializedName("amount_ml")
    val amountMl: Int,
    @SerializedName("timestamp")
    val timestamp: String,
    @SerializedName("is_drank")
    val isDrank: Boolean = true,
    @SerializedName("user_id")
    val userId: Int = 1
)