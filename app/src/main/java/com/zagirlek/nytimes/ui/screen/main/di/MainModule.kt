package com.zagirlek.nytimes.ui.screen.main.di

import com.arkivanov.decompose.ComponentContext
import com.zagirlek.nytimes.domain.usecase.AddWeatherPointUseCase
import com.zagirlek.nytimes.domain.usecase.DeleteWeatherPointUseCase
import com.zagirlek.nytimes.domain.usecase.GetCityAutocompleteUseCase
import com.zagirlek.nytimes.domain.usecase.GetOrPutCityUseCase
import com.zagirlek.nytimes.domain.usecase.GetPagingNewsUseCase
import com.zagirlek.nytimes.domain.usecase.GetRecentCityListUseCase
import com.zagirlek.nytimes.domain.usecase.GetWeatherPointsHistoryFlowUseCase
import com.zagirlek.nytimes.ui.screen.main.favorites.FavoritesComponent
import com.zagirlek.nytimes.ui.screen.main.favorites.cmp.DefaultFavoriteComponent
import com.zagirlek.nytimes.ui.screen.main.news.NewsComponent
import com.zagirlek.nytimes.ui.screen.main.news.cmp.DefaultNewsComponent
import com.zagirlek.nytimes.ui.screen.main.weather.WeatherComponent
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.DefaultWeatherComponent

class MainModule(
    private val getCityAutocompleteUseCase: GetCityAutocompleteUseCase,
    private val getWeatherPointsHistoryFlowUseCase: GetWeatherPointsHistoryFlowUseCase,
    private val getRecentCityListUseCase: GetRecentCityListUseCase,
    private val deleteWeatherPointUseCase: DeleteWeatherPointUseCase,
    private val addWeatherPointUseCase: AddWeatherPointUseCase,
    private val getOrPutCityUseCase: GetOrPutCityUseCase,
    private val getPagingNewsUseCase: GetPagingNewsUseCase
) {
    fun getWeatherComponent(
        componentContext: ComponentContext
    ): WeatherComponent =
        DefaultWeatherComponent(
            componentContext = componentContext,
            getCityAutocompleteUseCase = getCityAutocompleteUseCase,
            getWeatherPointsHistoryFlowUseCase = getWeatherPointsHistoryFlowUseCase,
            getRecentCityListUseCase = getRecentCityListUseCase,
            deleteWeatherPointUseCase = deleteWeatherPointUseCase,
            addWeatherPointUseCase = addWeatherPointUseCase,
            getOrPutCityUseCase = getOrPutCityUseCase
        )

    fun getNewsComponent(
        componentContext: ComponentContext
    ): NewsComponent =
        DefaultNewsComponent(
            componentContext = componentContext,
            getPagingNewsUseCase = getPagingNewsUseCase
        )

    fun getFavoritesComponent(
        componentContext: ComponentContext
    ): FavoritesComponent =
        DefaultFavoriteComponent(componentContext)
}