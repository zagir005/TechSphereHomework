package com.zagirlek.nytimes.core.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zagirlek.nytimes.core.local.entity.CityEntity
import com.zagirlek.nytimes.core.local.entity.WeatherInfoEntity
import com.zagirlek.nytimes.core.local.entity.WeatherWithCity

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(cityEntity: CityEntity): Long

    @Delete
    suspend fun deleteCity(cityEntity: CityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherInfo(weatherInfoEntity: WeatherInfoEntity): Long

    @Transaction
    @Query("SELECT * FROM weather_info WHERE cityId = :cityId ORDER BY createdAt DESC")
    suspend fun getWeatherByCity(cityId: Long): List<WeatherWithCity>

    @Delete
    suspend fun deleteWeatherInfo(weatherInfoEntity: WeatherInfoEntity)
}

