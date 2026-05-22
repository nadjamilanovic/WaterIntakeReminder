package com.example.waterintakereminder.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.waterintakereminder.data.local.entity.TipEntity

@Dao
interface TipDao {

    @Insert
    suspend fun insertTips(tips: List<TipEntity>)

    @Query("SELECT * FROM tips")
    suspend fun getAllTips(): List<TipEntity>
}