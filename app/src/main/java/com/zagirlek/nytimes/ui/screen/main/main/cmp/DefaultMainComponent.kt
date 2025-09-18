package com.zagirlek.nytimes.ui.screen.main.main.cmp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.router.pages.Pages
import com.arkivanov.decompose.router.pages.PagesNavigation
import com.arkivanov.decompose.router.pages.select
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.router.pages.*
import com.zagirlek.nytimes.ui.screen.main.favorites.FavoritesComponent
import com.zagirlek.nytimes.ui.screen.main.favorites.cmp.DefaultFavoriteComponent
import com.zagirlek.nytimes.ui.screen.main.main.MainComponent
import com.zagirlek.nytimes.ui.screen.main.main.MainComponent.Child
import com.zagirlek.nytimes.ui.screen.main.news.NewsComponent
import com.zagirlek.nytimes.ui.screen.main.news.cmp.DefaultNewsComponent
import com.zagirlek.nytimes.ui.screen.main.weather.WeatherComponent
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.DefaultWeatherComponent
import kotlinx.serialization.Serializable


class DefaultMainComponent(
    private val componentContext: ComponentContext
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

    private fun favorites(componentContext: ComponentContext): FavoritesComponent =
        DefaultFavoriteComponent(componentContext)

    private fun news(componentContext: ComponentContext): NewsComponent =
        DefaultNewsComponent(componentContext)

    private fun weather(componentContext: ComponentContext): WeatherComponent =
        DefaultWeatherComponent(componentContext)

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