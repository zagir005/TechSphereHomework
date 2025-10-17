package com.zagirlek.main.cmp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.router.pages.Pages
import com.arkivanov.decompose.router.pages.PagesNavigation
import com.arkivanov.decompose.router.pages.childPages
import com.arkivanov.decompose.router.pages.select
import com.arkivanov.decompose.value.Value
import com.zagirlek.favorite.FavoriteNewsScreen
import com.zagirlek.favorite.di.FavoriteNewsFeatureModule
import com.zagirlek.latest.LatestNewsScreen
import com.zagirlek.latest.di.LatestNewsFeatureModule
import com.zagirlek.main.MainComponent
import com.zagirlek.weather.WeatherScreen
import com.zagirlek.weather.di.WeatherFeatureModule
import kotlinx.serialization.Serializable

internal class DefaultMainComponent(
    private val componentContext: ComponentContext,
    private val weatherFeatureModule: WeatherFeatureModule,
    private val latestNewsFeatureModule: LatestNewsFeatureModule,
    private val favoriteNewsFeatureModule: FavoriteNewsFeatureModule
): ComponentContext by componentContext, MainComponent {
    private val navigation = PagesNavigation<Config>()

    override val pages: Value<ChildPages<Config, MainComponent.Child>> = childPages(
        source = navigation,
        serializer = Config.serializer(),
        initialPages = {
            Pages(
                items = listOf(
                    Config.Weather,
                    Config.News,
                    Config.Favorites
                ),
                selectedIndex = 0
            )
        },
        childFactory = ::child,
    )

    override fun selectPage(index: Int) {
         navigation.select(index)
    }

    private fun child(config: Config, component: ComponentContext): MainComponent.Child {
        return when(config){
            Config.Favorites -> MainComponent.Child.Favorites(component = favorites(componentContext = component))
            Config.News -> MainComponent.Child.News(component = latest(componentContext = component))
            Config.Weather -> MainComponent.Child.Weather(component = weather(componentContext = component))
        }
    }
    private fun weather(componentContext: ComponentContext): WeatherScreen =
        weatherFeatureModule.getWeatherComponent(componentContext = componentContext)
    private fun latest(componentContext: ComponentContext): LatestNewsScreen =
        latestNewsFeatureModule.getLatestNewsComponent(
            componentContext = componentContext,
        )
    private fun favorites(componentContext: ComponentContext): FavoriteNewsScreen =
        favoriteNewsFeatureModule.getFavoriteNewsComponent(
            componentContext = componentContext,
        )

    @Serializable
    sealed class Config{
        @Serializable
        object Weather: Config()

        @Serializable
        object Favorites: Config()

        @Serializable
        object News: Config()
    }
}