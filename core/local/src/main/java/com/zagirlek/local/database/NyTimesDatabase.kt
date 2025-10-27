package com.zagirlek.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zagirlek.local.computer.dao.ComputerDao
import com.zagirlek.local.computer.entity.ComputerEntity
import com.zagirlek.local.news.dao.ArticleFullDao
import com.zagirlek.local.news.dao.ArticleLiteDao
import com.zagirlek.local.news.dao.ArticleStatusDao
import com.zagirlek.local.news.dao.RemoteKeyDao
import com.zagirlek.local.news.entity.ArticleFullEntity
import com.zagirlek.local.news.entity.ArticleLiteEntity
import com.zagirlek.local.news.entity.ArticleStatusEntity
import com.zagirlek.local.news.entity.RemoteKeyEntity
import com.zagirlek.local.user.dao.UserDao
import com.zagirlek.local.user.entitiy.UserEntity
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
        RemoteKeyEntity::class,
        UserEntity::class,
        ComputerEntity::class
               ],
    version = 1
)
internal abstract class NyTimesDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun cityDao(): CityDao
    abstract fun articleLiteDao(): ArticleLiteDao
    abstract fun articleFullDao(): ArticleFullDao
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun articleStatusDao(): ArticleStatusDao
    abstract fun userDao(): UserDao
    abstract fun computerDao(): ComputerDao
}