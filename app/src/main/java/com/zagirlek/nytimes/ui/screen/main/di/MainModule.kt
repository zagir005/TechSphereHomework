package com.zagirlek.nytimes.ui.screen.main.di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.zagirlek.nytimes.domain.usecase.AddWeatherPointUseCase
import com.zagirlek.nytimes.domain.usecase.DeleteWeatherPointUseCase
import com.zagirlek.nytimes.domain.usecase.GetArticleStatusFlowUseCase
import com.zagirlek.nytimes.domain.usecase.GetCityAutocompleteUseCase
import com.zagirlek.nytimes.domain.usecase.GetOrPutCityUseCase
import com.zagirlek.nytimes.domain.usecase.GetPagingNewsUseCase
import com.zagirlek.nytimes.domain.usecase.GetRecentCityListUseCase
import com.zagirlek.nytimes.domain.usecase.GetWeatherPointsHistoryFlowUseCase
import com.zagirlek.nytimes.domain.usecase.UpdateArticleStatusUseCase
import com.zagirlek.nytimes.ui.screen.main.favorites.FavoritesComponent
import com.zagirlek.nytimes.ui.screen.main.favorites.cmp.DefaultFavoriteComponent
import com.zagirlek.nytimes.ui.screen.main.news.NewsScreen
import com.zagirlek.nytimes.ui.screen.main.news.NewsScreenComponent
import com.zagirlek.nytimes.ui.screen.main.weather.WeatherComponent
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.DefaultWeatherComponent

class MainModule(
    private val storeFactory: StoreFactory,
    private val getCityAutocompleteUseCase: GetCityAutocompleteUseCase,
    private val getWeatherPointsHistoryFlowUseCase: GetWeatherPointsHistoryFlowUseCase,
    private val getRecentCityListUseCase: GetRecentCityListUseCase,
    private val deleteWeatherPointUseCase: DeleteWeatherPointUseCase,
    private val addWeatherPointUseCase: AddWeatherPointUseCase,
    private val getOrPutCityUseCase: GetOrPutCityUseCase,
    private val getPagingNewsUseCase: GetPagingNewsUseCase,
    private val updateArticleStatusUseCase: UpdateArticleStatusUseCase,
    private val getArticleStatusFlowUseCase: GetArticleStatusFlowUseCase
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
        NewsScreenComponent(
            componentContext = componentContext,
            storeFactory = storeFactory,
            getPagingNewsUseCase = getPagingNewsUseCase,
            updateArticleStatusUseCase = updateArticleStatusUseCase,
            getArticleStatusFlowUseCase = getArticleStatusFlowUseCase
        )

    fun getFavoritesComponent(
        componentContext: ComponentContext
    ): FavoritesComponent =
        DefaultFavoriteComponent(componentContext)
}