package com.zagirlek.nytimes.domain

import com.zagirlek.auth.usecase.AuthUseCase
import com.zagirlek.auth.usecase.AuthWithoutLoginUseCase
import com.zagirlek.auth.usecase.GetCurrentAuthTokenUseCase
import com.zagirlek.news.usecase.FavoriteNewsFlowUseCase
import com.zagirlek.news.usecase.GetArticleFullFlowUseCase
import com.zagirlek.news.usecase.LatestNewsPagingUseCase
import com.zagirlek.news.usecase.ToggleArticleFavoriteStatusUseCase
import com.zagirlek.news.usecase.ToggleArticleReadStatusUseCase
import com.zagirlek.weather.usecase.AddWeatherPointUseCase
import com.zagirlek.weather.usecase.DeleteWeatherPointUseCase
import com.zagirlek.weather.usecase.GetCityAutocompleteUseCase
import com.zagirlek.weather.usecase.GetOrPutCityUseCase
import com.zagirlek.weather.usecase.GetRecentCityListUseCase
import com.zagirlek.weather.usecase.GetWeatherPointsHistoryFlowUseCase

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
    fun getLatestNewsPagingUseCase(): LatestNewsPagingUseCase
    fun getFavoriteNewsFlowUseCase(): FavoriteNewsFlowUseCase

    fun toggleArticleFavoriteStatusUseCase(): ToggleArticleFavoriteStatusUseCase
    fun toggleArticleReadStatusUseCase(): ToggleArticleReadStatusUseCase
    fun getArticleFullByIdFlowUseCase(): GetArticleFullFlowUseCase
}