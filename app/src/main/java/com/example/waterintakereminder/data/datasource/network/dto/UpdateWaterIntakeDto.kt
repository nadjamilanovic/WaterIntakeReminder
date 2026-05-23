package com.example.waterintakereminder.data.datasource.network.dto

import com.google.gson.annotations.SerializedName

data class UpdateWaterIntakeDto(
    @SerializedName("amount_ml")
    val amountMl: Int? = null,
    @SerializedName("timestamp")
    val timestamp: String? = null,
    @SerializedName("is_drank")
    val isDrank: Boolean? = null,
    @SerializedName("user_id")
    val userId: Int? = null
)