package com.zagirlek.nytimes.di

import com.zagirlek.auth.di.AuthDomainModule
import com.zagirlek.news.di.NewsDomainModule
import com.zagirlek.weather.di.WeatherDomainModule

class DomainDi(
    private val dataDi: DataDi
) {
    val authDomainModule: AuthDomainModule by lazy {
        AuthDomainModule(
            authRepository = dataDi.authDataModule.authRepository
        )
    }
    val weatherDomainModule: WeatherDomainModule by lazy {
        WeatherDomainModule(
            cityAutocompleteRepository = dataDi.weatherDataModule.cityAutocompleteRepository,
            cityRepository = dataDi.weatherDataModule.cityRepository,
            weatherRepository = dataDi.weatherDataModule.weatherRepository
        )
    }
    val newsDomainModule: NewsDomainModule by lazy {
        NewsDomainModule(
            newsRepository = dataDi.newsDataModule.newsRepository,
            articleRepository = dataDi.newsDataModule.articleRepository,
            articleStatusRepository = dataDi.newsDataModule.articleStatusRepository
        )
    }
}