package com.zagirlek.nytimes.ui.screen.main.weather

import com.arkivanov.decompose.value.Value
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherAction
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherEffect
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherState
import kotlinx.coroutines.flow.SharedFlow

interface WeatherComponent {
    val state: Value<WeatherState>
    val effect: SharedFlow<WeatherEffect>

    fun action(action: WeatherAction)
}