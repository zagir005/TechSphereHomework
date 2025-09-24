package com.zagirlek.nytimes.ui.screen.main.weather

import com.arkivanov.decompose.value.Value
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherAction
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherState

interface WeatherComponent {
    val state: Value<WeatherState>

    fun action(action: WeatherAction)
}