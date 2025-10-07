package com.zagirlek.nytimes.domain.usecase.di


import com.zagirlek.nytimes.domain.usecase.auth.AuthUseCase
import com.zagirlek.nytimes.domain.usecase.auth.AuthWithoutLoginUseCase
import com.zagirlek.nytimes.domain.usecase.auth.GetCurrentAuthTokenUseCase
import com.zagirlek.nytimes.domain.usecase.news.GetArticleFullByIdUseCase
import com.zagirlek.nytimes.domain.usecase.news.GetPagingFavoriteNewsUseCase
import com.zagirlek.nytimes.domain.usecase.news.GetPagingNewsUseCase
import com.zagirlek.nytimes.domain.usecase.news.ToggleArticleFavoriteStatusUseCase
import com.zagirlek.nytimes.domain.usecase.news.ToggleArticleReadStatusUseCase
import com.zagirlek.nytimes.domain.usecase.weather.AddWeatherPointUseCase
import com.zagirlek.nytimes.domain.usecase.weather.DeleteWeatherPointUseCase
import com.zagirlek.nytimes.domain.usecase.weather.GetCityAutocompleteUseCase
import com.zagirlek.nytimes.domain.usecase.weather.GetOrPutCityUseCase
import com.zagirlek.nytimes.domain.usecase.weather.GetRecentCityListUseCase
import com.zagirlek.nytimes.domain.usecase.weather.GetWeatherPointsHistoryFlowUseCase

interface UseCaseModule {
    fun addWeatherPointUseCase(): AddWeatherPointUseCase
    fun deleteWeatherPointUseCase(): DeleteWeatherPointUseCase
    fun getWeatherPointsHistoryFlowUseCase(): GetWeatherPointsHistoryFlowUseCase
    fun getCityAutocompleteUseCase(): GetCityAutocompleteUseCase
    fun getRecentCityListUseCase(): GetRecentCityListUseCase
    fun getOrPutCityUseCase(): GetOrPutCityUseCase

    fun authUseCase(): AuthUseCase
    fun authWithoutLoginUseCase(): AuthWithoutLoginUseCase
    fun getCurrentAuthTokenUseCase(): GetCurrentAuthTokenUseCase

    fun getArticleFullByIdUseCase(): GetArticleFullByIdUseCase
    fun getPagingNewsUseCase(): GetPagingNewsUseCase
    fun getPagingFavoriteNewsUseCase(): GetPagingFavoriteNewsUseCase

    fun toggleArticleFavoriteStatusUseCase(): ToggleArticleFavoriteStatusUseCase
    fun toggleArticleReadStatusUseCase(): ToggleArticleReadStatusUseCase

}