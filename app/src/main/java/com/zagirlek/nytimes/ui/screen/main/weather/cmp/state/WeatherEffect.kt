package com.zagirlek.nytimes.ui.screen.main.weather.cmp.state

sealed class WeatherEffect {
    data object ShowCitySaveErrorErrorDialog: WeatherEffect()
    data object ShowWeatherPointAddErrorDialog: WeatherEffect()
    data object ShowCitySelectErrorDialog: WeatherEffect()
}