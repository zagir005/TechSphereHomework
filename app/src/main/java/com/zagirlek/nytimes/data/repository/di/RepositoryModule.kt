package com.zagirlek.nytimes.data.repository.di

import com.zagirlek.android.networkchecker.NetworkConnectionChecker
import com.zagirlek.local.news.dao.ArticleFullDao
import com.zagirlek.local.news.dao.ArticleLiteDao
import com.zagirlek.local.news.dao.ArticleStatusDao
import com.zagirlek.local.weather.dao.CityDao
import com.zagirlek.local.weather.dao.WeatherDao
import com.zagirlek.nytimes.data.repository.ArticleRepositoryImpl
import com.zagirlek.nytimes.data.repository.ArticleStatusRepositoryImpl
import com.zagirlek.nytimes.data.repository.CityAutocompleteRepositoryImpl
import com.zagirlek.nytimes.data.repository.CityRepositoryImpl
import com.zagirlek.nytimes.data.repository.MockAuthRepositoryImpl
import com.zagirlek.nytimes.data.repository.NewsRepositoryImpl
import com.zagirlek.nytimes.data.repository.WeatherRepositoryImpl
import com.zagirlek.nytimes.domain.repository.ArticleRepository
import com.zagirlek.nytimes.domain.repository.ArticleStatusRepository
import com.zagirlek.nytimes.domain.repository.AuthRepository
import com.zagirlek.nytimes.domain.repository.CityAutocompleteRepository
import com.zagirlek.nytimes.domain.repository.CityRepository
import com.zagirlek.nytimes.domain.repository.NewsRepository
import com.zagirlek.nytimes.domain.repository.WeatherRepository
import com.zagirlek.remote.autocomplete.service.AutocompleteService
import com.zagirlek.remote.extractor.RemoteNewsExtractorSource
import com.zagirlek.remote.news.RemoteNewsSource

class RepositoryModule(
    private val autocompleteService: AutocompleteService,
    private val remoteNewsSource: RemoteNewsSource,
    private val remoteNewsExtractorSource: RemoteNewsExtractorSource,
    private val weatherDao: WeatherDao,
    private val cityDao: CityDao,
    private val articleFullDao: ArticleFullDao,
    private val articleLiteDao: ArticleLiteDao,
    private val articleStatusDao: ArticleStatusDao,
    private val connectionChecker: NetworkConnectionChecker
) {
    val authRepository: AuthRepository by lazy {
        MockAuthRepositoryImpl(
            networkConnectionChecker = connectionChecker
        )
    }
    val cityAutocompleteRepository: CityAutocompleteRepository by lazy {
        CityAutocompleteRepositoryImpl(
            autocompleteService = autocompleteService
        )
    }
    val cityRepository: CityRepository by lazy {
        CityRepositoryImpl(
            cityDao = cityDao
        )
    }
    val weatherRepository: WeatherRepository by lazy {
        WeatherRepositoryImpl(
            weatherDao = weatherDao
        )
    }
    val newsRepository: NewsRepository by lazy {
        NewsRepositoryImpl(
            articleLiteDao = articleLiteDao,
            remoteNewsSource = remoteNewsSource
        )
    }
    val articleRepository: ArticleRepository by lazy {
        ArticleRepositoryImpl(
            articleFullDao = articleFullDao,
            articleStatusDao = articleStatusDao,
            articleLiteDao = articleLiteDao,
            remoteNewsSource = remoteNewsSource,
            remoteExtractorNewsSource = remoteNewsExtractorSource,
            networkConnectionChecker = connectionChecker
        )
    }

    val articleStatusRepository: ArticleStatusRepository by lazy {
        ArticleStatusRepositoryImpl(
            articleStatusDao = articleStatusDao,
            articleRepository = articleRepository
        )
    }
}