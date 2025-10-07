package com.zagirlek.nytimes.ui.main.di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.zagirlek.nytimes.domain.usecase.news.GetPagingNewsUseCase
import com.zagirlek.nytimes.domain.usecase.news.ToggleArticleFavoriteStatusUseCase
import com.zagirlek.nytimes.domain.usecase.news.ToggleArticleReadStatusUseCase
import com.zagirlek.nytimes.domain.usecase.weather.AddWeatherPointUseCase
import com.zagirlek.nytimes.domain.usecase.weather.DeleteWeatherPointUseCase
import com.zagirlek.nytimes.domain.usecase.weather.GetCityAutocompleteUseCase
import com.zagirlek.nytimes.domain.usecase.weather.GetOrPutCityUseCase
import com.zagirlek.nytimes.domain.usecase.weather.GetRecentCityListUseCase
import com.zagirlek.nytimes.domain.usecase.weather.GetWeatherPointsHistoryFlowUseCase
import com.zagirlek.nytimes.ui.main.news.NewsScreen
import com.zagirlek.nytimes.ui.main.news.NewsScreenComponent
import com.zagirlek.nytimes.ui.main.weather.WeatherComponent
import com.zagirlek.nytimes.ui.main.weather.cmp.DefaultWeatherComponent

class MainModule(
    private val storeFactory: StoreFactory,
    private val getCityAutocompleteUseCase: GetCityAutocompleteUseCase,
    private val getWeatherPointsHistoryFlowUseCase: GetWeatherPointsHistoryFlowUseCase,
    private val getRecentCityListUseCase: GetRecentCityListUseCase,
    private val deleteWeatherPointUseCase: DeleteWeatherPointUseCase,
    private val addWeatherPointUseCase: AddWeatherPointUseCase,
    private val getOrPutCityUseCase: GetOrPutCityUseCase,
    private val getPagingNewsUseCase: GetPagingNewsUseCase,
    private val toggleArticleFavoriteStatusUseCase: ToggleArticleFavoriteStatusUseCase,
    private val toggleArticleReadStatusUseCase: ToggleArticleReadStatusUseCase
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
    ): NewsScreen =
        NewsScreenComponent (
            componentContext = componentContext,
            storeFactory = storeFactory,
            getPagingNewsUseCase = getPagingNewsUseCase,
            toggleFavoriteStatusUseCase = toggleArticleFavoriteStatusUseCase,
            toggleReadStatusUseCase = toggleArticleReadStatusUseCase
        )
}