package com.zagirlek.nytimes.ui.screen.main.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.value.Value
import com.zagirlek.nytimes.R
import com.zagirlek.nytimes.ui.screen.main.favorites.FavoritesComponent
import com.zagirlek.nytimes.ui.screen.main.news.NewsComponent
import com.zagirlek.nytimes.ui.screen.main.weather.WeatherComponent

interface MainComponent {
    val pages: Value<ChildPages<*, Child>>

    fun selectTab(index: Int)

    sealed class Child(val nameResource: Int, val iconResource: Int){
        data class Weather(val component: WeatherComponent): Child(
            R.string.weather,
            R.drawable.outline_news_24
        )
        data class News(val component: NewsComponent): Child(
            R.string.weather,
            R.drawable.outline_news_24
        )
        data class Favorites(val component: FavoritesComponent): Child(
            R.string.weather,
            R.drawable.outline_news_24
        )
    }
}