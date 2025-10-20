package com.zagirlek.root

import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.value.Value
import com.zagirlek.favorite.FavoriteNewsScreen
import com.zagirlek.latest.LatestNewsScreen
import com.zagirlek.weather.WeatherScreen

interface ClientRootComponent {
    val pages: Value<ChildPages<*, Child>>
    fun selectPage(index: Int)
    sealed class Child(){
        data class Weather(val component: WeatherScreen): Child()
        data class News(val component: LatestNewsScreen): Child()
        data class Favorites(val component: FavoriteNewsScreen): Child()
    }
}
