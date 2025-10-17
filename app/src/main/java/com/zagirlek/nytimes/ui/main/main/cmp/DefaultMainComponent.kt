package com.zagirlek.nytimes.ui.main.main.cmp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.router.pages.Pages
import com.arkivanov.decompose.router.pages.PagesNavigation
import com.arkivanov.decompose.router.pages.childPages
import com.arkivanov.decompose.router.pages.select
import com.arkivanov.decompose.value.Value
import com.zagirlek.favorite.FavoriteNewsScreen
import com.zagirlek.latest.LatestNewsScreen
import com.zagirlek.nytimes.ui.main.di.MainModule
import com.zagirlek.nytimes.ui.main.main.MainComponent
import com.zagirlek.nytimes.ui.main.main.MainComponent.Child
import com.zagirlek.weather.WeatherScreen
import kotlinx.serialization.Serializable

class DefaultMainComponent(
    private val componentContext: ComponentContext,
    private val mainModule: MainModule
): ComponentContext by componentContext, MainComponent {
    private val navigation = PagesNavigation<Config>()

    override val pages: Value<ChildPages<Config, Child>> = childPages(
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

    private fun child(config: Config, component: ComponentContext): Child{
        return when(config){
            Config.Favorites -> Child.Favorites(component = favorites(componentContext = component))
            Config.News -> Child.News(component = news(componentContext = component))
            Config.Weather -> Child.Weather(component = weather(componentContext = component))
        }
    }

    private fun favorites(componentContext: ComponentContext): FavoriteNewsScreen =
        mainModule.getFavoriteNewsComponent(
            componentContext = componentContext,
        )

    private fun news(componentContext: ComponentContext): LatestNewsScreen =
        mainModule.getLatestNewsComponent(
            componentContext = componentContext,
        )

    private fun weather(componentContext: ComponentContext): WeatherScreen =
        mainModule.getWeatherComponent(componentContext = componentContext)

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