package com.zagirlek.main.di

import com.arkivanov.decompose.ComponentContext
import com.zagirlek.favorite.di.FavoriteNewsFeatureModule
import com.zagirlek.latest.di.LatestNewsFeatureModule
import com.zagirlek.main.MainComponent
import com.zagirlek.main.cmp.DefaultMainComponent
import com.zagirlek.weather.di.WeatherFeatureModule

class MainFeatureModule(
    private val weatherFeatureModule: WeatherFeatureModule,
    private val latestNewsFeatureModule: LatestNewsFeatureModule,
    private val favoriteNewsFeatureModule: FavoriteNewsFeatureModule
) {
    fun getMainComponent(
        componentContext: ComponentContext
    ): MainComponent =
        DefaultMainComponent(
            componentContext = componentContext,
            weatherFeatureModule = weatherFeatureModule,
            latestNewsFeatureModule = latestNewsFeatureModule,
            favoriteNewsFeatureModule = favoriteNewsFeatureModule,
        )
}