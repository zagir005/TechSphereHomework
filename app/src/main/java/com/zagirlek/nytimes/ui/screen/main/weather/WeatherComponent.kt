package com.zagirlek.nytimes.ui.screen.main.weather

import com.arkivanov.decompose.value.Value
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherState
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherUiAction

interface WeatherComponent {
    val state: Value<WeatherState>

    fun action(action: WeatherUiAction)
}