package com.zagirlek.nytimes.ui.screen.main.weather.cmp.state

sealed class WeatherAction {
    data object AddWeatherPoint: WeatherAction()

    data class DeleteWeatherPoint(val id: Long): WeatherAction()
}