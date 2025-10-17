package com.zagirlek.local.weather.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zagirlek.local.weather.entity.WeatherInfoEntity
import com.zagirlek.local.weather.entity.WeatherWithCity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherInfo(weatherInfoEntity: WeatherInfoEntity): Long

    @Transaction
    @Query("SELECT * FROM weather_info WHERE cityId = :cityId ORDER BY createdAt DESC")
    fun getWeatherByCityFlow(cityId: Long): Flow<List<WeatherWithCity>>

    @Transaction
    @Query("SELECT * FROM weather_info ORDER BY createdAt DESC")
    fun getWeatherByOfAllCities(): Flow<List<WeatherWithCity>>

    @Query("SELECT * FROM weather_info WHERE id = :id")
    suspend fun getWeatherInfoById(id: Long): WeatherWithCity?

    @Query("DELETE FROM weather_info WHERE id = :id")
    suspend fun deleteWeatherInfoById(id: Long)
}

