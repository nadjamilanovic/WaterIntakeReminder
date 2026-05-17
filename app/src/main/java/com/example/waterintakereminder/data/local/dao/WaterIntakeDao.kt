package com.example.waterintakereminder.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.waterintakereminder.data.local.entity.WaterIntakeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterIntakeDao {

    @Insert
    suspend fun insertWaterIntake(waterIntake: WaterIntakeEntity)

    @Update
    suspend fun updateWaterIntake(waterIntake: WaterIntakeEntity)

    @Delete
    suspend fun deleteWaterIntake(waterIntake: WaterIntakeEntity)

    @Query("SELECT * FROM water_intakes ORDER BY timestamp DESC")
    fun getAllWaterIntakes(): Flow<List<WaterIntakeEntity>>

    @Query("DELETE FROM water_intakes")
    suspend fun deleteAllWaterIntakes()
}