package com.zagirlek.nytimes.ui.main.di

import com.arkivanov.decompose.ComponentContext
import com.zagirlek.favorite.FavoriteNewsScreen
import com.zagirlek.favorite.di.FavoriteNewsFeatureModule
import com.zagirlek.latest.LatestNewsScreen
import com.zagirlek.latest.di.LatestNewsFeatureModule
import com.zagirlek.weather.WeatherScreen
import com.zagirlek.weather.di.WeatherFeatureModule

class MainModule(
    private val weatherFeatureModule: WeatherFeatureModule,
    private val latestNewsFeatureModule: LatestNewsFeatureModule,
    private val favoriteNewsFeatureModule: FavoriteNewsFeatureModule,
) {
    fun getWeatherComponent(
        componentContext: ComponentContext
    ): WeatherScreen =
        weatherFeatureModule.getWeatherComponent(
            componentContext = componentContext
        )

    fun getLatestNewsComponent(
        componentContext: ComponentContext,
    ): LatestNewsScreen =
        latestNewsFeatureModule.getLatestNewsComponent(
            componentContext = componentContext
        )

    fun getFavoriteNewsComponent(
        componentContext: ComponentContext,
    ): FavoriteNewsScreen =
        favoriteNewsFeatureModule.getFavoriteNewsComponent(
            componentContext = componentContext
        )
}