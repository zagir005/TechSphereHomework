package com.zagirlek.nytimes.di

import android.content.Context
import androidx.room.Room
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.zagirlek.nytimes.core.networkchecker.NetworkConnectionChecker
import com.zagirlek.nytimes.core.networkchecker.NetworkConnectionCheckerImpl
import com.zagirlek.nytimes.data.local.NyTimesDatabase
import com.zagirlek.nytimes.data.network.NetworkModule

class AppDi(applicationContext: Context) {
    private val database: NyTimesDatabase = Room.databaseBuilder(
        context = applicationContext,
        klass = NyTimesDatabase::class.java,
        name = "nyTimesDatabase"
    )
        .fallbackToDestructiveMigration(true)
        .build()

    val networkModule = NetworkModule

    val networkConnectionChecker: NetworkConnectionChecker by lazy {
        NetworkConnectionCheckerImpl(applicationContext)
    }
    val storeFactory: StoreFactory by lazy {
        DefaultStoreFactory()
    }

    fun getDatabase(): NyTimesDatabase = database
}