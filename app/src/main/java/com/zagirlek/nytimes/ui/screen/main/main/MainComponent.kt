package com.zagirlek.nytimes.ui.screen.main.main

import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.value.Value
import com.zagirlek.nytimes.ui.screen.main.favorites.FavoritesComponent
import com.zagirlek.nytimes.ui.screen.main.news.NewsScreen
import com.zagirlek.nytimes.ui.screen.main.weather.WeatherComponent

interface MainComponent {
    val pages: Value<ChildPages<*, Child>>

    fun selectPage(index: Int)

    sealed class Child(){
        data class Weather(val component: WeatherComponent): Child()
        data class News(val component: NewsScreen): Child()
        data class Favorites(val component: FavoritesComponent): Child()
    }
}
