package com.example.waterintakereminder.data.datasource.network.service

import com.example.waterintakereminder.data.datasource.network.dto.CreateWaterIntakeDto
import com.example.waterintakereminder.data.datasource.network.dto.UpdateWaterIntakeDto
import com.example.waterintakereminder.data.datasource.network.dto.WaterIntakeDto
import retrofit2.http.*

interface WaterIntakeApiService {

    @GET("water-intakes/")
    suspend fun getAllWaterIntakes(
        @Header("X-Authentication") authHeader: String = "yes"
    ): List<WaterIntakeDto>

    @GET("water-intakes/{id}")
    suspend fun getWaterIntakeById(
        @Path("id") id: Int,
        @Header("X-Authentication") authHeader: String = "yes"
    ): WaterIntakeDto

    @POST("water-intakes/")
    suspend fun createWaterIntake(
        @Body createWaterIntakeDto: CreateWaterIntakeDto,
        @Header("X-Authentication") authHeader: String = "yes"
    ): WaterIntakeDto

    @PUT("water-intakes/{id}")
    suspend fun updateWaterIntake(
        @Path("id") id: Int,
        @Body updateWaterIntakeDto: UpdateWaterIntakeDto,
        @Header("X-Authentication") authHeader: String = "yes"
    ): WaterIntakeDto

    @DELETE("water-intakes/{id}")
    suspend fun deleteWaterIntake(
        @Path("id") id: Int,
        @Header("X-Authentication") authHeader: String = "yes"
    ): Unit
}