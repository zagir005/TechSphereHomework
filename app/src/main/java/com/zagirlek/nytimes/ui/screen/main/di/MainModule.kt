package com.zagirlek.nytimes.ui.screen.main.di

import com.arkivanov.decompose.ComponentContext
import com.zagirlek.nytimes.domain.usecase.di.UseCaseModule
import com.zagirlek.nytimes.ui.screen.main.favorites.FavoritesComponent
import com.zagirlek.nytimes.ui.screen.main.favorites.cmp.DefaultFavoriteComponent
import com.zagirlek.nytimes.ui.screen.main.news.NewsComponent
import com.zagirlek.nytimes.ui.screen.main.news.cmp.DefaultNewsComponent
import com.zagirlek.nytimes.ui.screen.main.weather.WeatherComponent
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.DefaultWeatherComponent

class MainModule(
    private val useCaseModule: UseCaseModule
) {
    fun getWeatherComponent(
        componentContext: ComponentContext
    ): WeatherComponent =
        DefaultWeatherComponent(
            componentContext = componentContext,
            getCityAutocompleteUseCase = useCaseModule.getCityAutocompleteUseCase(),
            getWeatherPointsHistoryFlowUseCase = useCaseModule.getWeatherPointsHistoryFlowUseCase(),
            getRecentCityListUseCase = useCaseModule.getRecentCityListUseCase(),
            deleteWeatherPointUseCase = useCaseModule.deleteWeatherPointUseCase(),
            addWeatherPointUseCase = useCaseModule.addWeatherPointUseCase(),
            getOrPutCityUseCase = useCaseModule.getOrPutCityUseCase()
        )

    fun getNewsComponent(
        componentContext: ComponentContext
    ): NewsComponent =
        DefaultNewsComponent(componentContext)

    fun getFavoritesComponent(
        componentContext: ComponentContext
    ): FavoritesComponent =
        DefaultFavoriteComponent(componentContext)
}