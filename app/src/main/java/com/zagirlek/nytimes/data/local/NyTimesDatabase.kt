package com.zagirlek.nytimes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zagirlek.nytimes.data.local.news.dao.ArticleLiteDao
import com.zagirlek.nytimes.data.local.news.dao.RemoteKeyDao
import com.zagirlek.nytimes.data.local.news.entity.ArticleLiteEntity
import com.zagirlek.nytimes.data.local.news.entity.RemoteKeyEntity
import com.zagirlek.nytimes.data.local.weather.dao.CityDao
import com.zagirlek.nytimes.data.local.weather.dao.WeatherDao
import com.zagirlek.nytimes.data.local.weather.entity.CityEntity
import com.zagirlek.nytimes.data.local.weather.entity.WeatherInfoEntity

@Database(
    entities = [
        CityEntity::class,
        WeatherInfoEntity::class,
        ArticleLiteEntity::class,
        RemoteKeyEntity::class
               ],
    version = 1
)
abstract class NyTimesDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun cityDao(): CityDao
    abstract fun articleDao(): ArticleLiteDao

    abstract fun remoteKeyDao(): RemoteKeyDao
}