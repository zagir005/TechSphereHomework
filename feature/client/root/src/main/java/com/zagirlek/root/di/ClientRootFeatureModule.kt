package com.zagirlek.root.di

import com.arkivanov.decompose.ComponentContext
import com.zagirlek.favorite.di.FavoriteNewsFeatureModule
import com.zagirlek.latest.di.LatestNewsFeatureModule
import com.zagirlek.root.ClientRootComponent
import com.zagirlek.root.DefaultClientRootComponent
import com.zagirlek.weather.di.WeatherFeatureModule

class ClientRootFeatureModule(
    private val weatherFeatureModule: WeatherFeatureModule,
    private val latestNewsFeatureModule: LatestNewsFeatureModule,
    private val favoriteNewsFeatureModule: FavoriteNewsFeatureModule
) {
    fun getMainComponent(
        componentContext: ComponentContext
    ): ClientRootComponent =
        DefaultClientRootComponent(
            componentContext = componentContext,
            weatherFeatureModule = weatherFeatureModule,
            latestNewsFeatureModule = latestNewsFeatureModule,
            favoriteNewsFeatureModule = favoriteNewsFeatureModule,
        )
}