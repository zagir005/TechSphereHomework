package com.zagirlek.nytimes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zagirlek.nytimes.data.local.dao.CityDao
import com.zagirlek.nytimes.data.local.dao.WeatherDao
import com.zagirlek.nytimes.data.local.entity.CityEntity
import com.zagirlek.nytimes.data.local.entity.WeatherInfoEntity

@Database(
    entities = [CityEntity::class, WeatherInfoEntity::class],
    version = 1
)
abstract class NyTimesDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    abstract fun cityDao(): CityDao
}