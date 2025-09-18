package com.zagirlek.nytimes.core.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zagirlek.nytimes.core.local.dao.WeatherDao
import com.zagirlek.nytimes.core.local.entity.CityEntity
import com.zagirlek.nytimes.core.local.entity.WeatherInfoEntity

@Database(
    entities = [CityEntity::class, WeatherInfoEntity::class],
    version = 1
)
abstract class NyTimesDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}