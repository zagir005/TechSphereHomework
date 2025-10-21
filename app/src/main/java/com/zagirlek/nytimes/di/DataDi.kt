package com.zagirlek.nytimes.di

import com.zagirlek.auth.di.AuthDataModule
import com.zagirlek.news.di.NewsDataModule
import com.zagirlek.user.di.UserDataModule
import com.zagirlek.weather.di.WeatherDataModule

class DataDi(
    private val coreDi: CoreDi
) {
    val authDataModule: AuthDataModule by lazy {
        AuthDataModule(
            networkConnectionChecker = coreDi.networkConnectionChecker
        )
    }
    val weatherDataModule: WeatherDataModule by lazy {
        WeatherDataModule(
            autocompleteCitySource = coreDi.coreRemoteModule.remoteAutocompleteSource,
            cityDao = coreDi.coreLocalModule.cityDao(),
            weatherDao = coreDi.coreLocalModule.weatherDao()
        )

    }
    val newsDataModule: NewsDataModule by lazy {
        NewsDataModule(
            networkConnectionChecker = coreDi.networkConnectionChecker,
            articleFullDao = coreDi.coreLocalModule.articleFullDao(),
            articleLiteDao = coreDi.coreLocalModule.articleLiteDao(),
            articleStatusDao = coreDi.coreLocalModule.articleStatusDao(),
            remoteNewsSource = coreDi.coreRemoteModule.remoteNewsSource,
            remoteExtractorNewsSource = coreDi.coreRemoteModule.remoteNewsExtractorSource
        )
    }
    val userDataModule: UserDataModule by lazy {
        UserDataModule(
            userDao = coreDi.coreLocalModule.userDao()
        )
    }
}