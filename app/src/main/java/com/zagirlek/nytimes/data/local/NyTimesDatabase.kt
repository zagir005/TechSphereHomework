package com.zagirlek.nytimes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zagirlek.nytimes.data.local.news.dao.ArticleFullDao
import com.zagirlek.nytimes.data.local.news.dao.ArticleLiteDao
import com.zagirlek.nytimes.data.local.news.dao.ArticleStatusDao
import com.zagirlek.nytimes.data.local.news.dao.RemoteKeyDao
import com.zagirlek.nytimes.data.local.news.entity.ArticleFullEntity
import com.zagirlek.nytimes.data.local.news.entity.ArticleLiteEntity
import com.zagirlek.nytimes.data.local.news.entity.ArticleStatusEntity
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
        ArticleStatusEntity::class,
        ArticleFullEntity::class,
        RemoteKeyEntity::class
               ],
    version = 1
)
abstract class NyTimesDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun cityDao(): CityDao
    abstract fun articleLiteDao(): ArticleLiteDao
    abstract fun articleFullDao(): ArticleFullDao
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun articleStatusDao(): ArticleStatusDao
}