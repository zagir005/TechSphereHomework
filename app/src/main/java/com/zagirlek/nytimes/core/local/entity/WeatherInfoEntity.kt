package com.zagirlek.nytimes.core.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_info")
data class WeatherInfoEntity(
    val cityId: Long,
    val degree: Int,
    val createdAt: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
