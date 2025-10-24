package com.zagirlek.root

import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.value.Value
import com.zagirlek.favorite.FavoriteNewsScreen
import com.zagirlek.latest.LatestNewsScreen
import com.zagirlek.root.model.SessionModel
import com.zagirlek.weather.WeatherScreen
import kotlinx.coroutines.flow.StateFlow

interface ClientRootComponent {
    val pages: Value<ChildPages<*, Child>>
    val model: StateFlow<SessionModel?>
    fun selectPage(index: Int)
    fun logout()
    sealed class Child(){
        data class Weather(val component: WeatherScreen): Child()
        data class News(val component: LatestNewsScreen): Child()
        data class Favorites(val component: FavoriteNewsScreen): Child()
    }
}
