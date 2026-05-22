package com.example.waterintakereminder.data.local.util

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Long? {
        return value
    }
}