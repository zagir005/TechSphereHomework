package com.zagirlek.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zagirlek.local.news.dao.ArticleFullDao
import com.zagirlek.local.news.dao.ArticleLiteDao
import com.zagirlek.local.news.dao.ArticleStatusDao
import com.zagirlek.local.news.dao.RemoteKeyDao
import com.zagirlek.local.news.entity.ArticleFullEntity
import com.zagirlek.local.news.entity.ArticleLiteEntity
import com.zagirlek.local.news.entity.ArticleStatusEntity
import com.zagirlek.local.news.entity.RemoteKeyEntity
import com.zagirlek.local.weather.dao.CityDao
import com.zagirlek.local.weather.dao.WeatherDao
import com.zagirlek.local.weather.entity.CityEntity
import com.zagirlek.local.weather.entity.WeatherInfoEntity

@Database(
    entities = [
        CityEntity::class,
        WeatherInfoEntity::class,
        ArticleLiteEntity::class,
        ArticleStatusEntity::class,
        ArticleFullEntity::class,
        RemoteKeyEntity::class
               ],
    version = 2
)
internal abstract class NyTimesDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun cityDao(): CityDao
    abstract fun articleLiteDao(): ArticleLiteDao
    abstract fun articleFullDao(): ArticleFullDao
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun articleStatusDao(): ArticleStatusDao
}