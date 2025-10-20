package com.zagirlek.local.di

import android.content.Context
import androidx.room.Room
import com.zagirlek.local.database.NyTimesDatabase

class CoreLocalModule(private val applicationContext: Context) {
    private val database: NyTimesDatabase by lazy {
        Room.databaseBuilder(
            context = applicationContext,
            klass = NyTimesDatabase::class.java,
            name = "nyTimesDatabase"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    fun cityDao() = database.cityDao()
    fun weatherDao() = database.weatherDao()
    fun remoteKeyDao() = database.remoteKeyDao()
    fun userDao() = database.userDao()
    fun articleStatusDao() = database.articleStatusDao()
    fun articleLiteDao() = database.articleLiteDao()
    fun articleFullDao() = database.articleFullDao()

}