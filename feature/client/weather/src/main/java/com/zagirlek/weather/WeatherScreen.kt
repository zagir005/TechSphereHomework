package com.zagirlek.weather

import com.arkivanov.decompose.value.Value
import com.zagirlek.weather.cmp.state.WeatherAction
import com.zagirlek.weather.cmp.state.WeatherEffect
import com.zagirlek.weather.cmp.state.WeatherState
import kotlinx.coroutines.flow.SharedFlow

interface WeatherScreen {
    val state: Value<WeatherState>
    val effect: SharedFlow<WeatherEffect>

    fun action(action: WeatherAction)
}