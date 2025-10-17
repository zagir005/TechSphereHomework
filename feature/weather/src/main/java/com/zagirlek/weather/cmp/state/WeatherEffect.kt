package com.zagirlek.weather.cmp.state

sealed class WeatherEffect {
    data object ShowCitySaveErrorErrorDialog: WeatherEffect()
    data object ShowWeatherPointAddErrorDialog: WeatherEffect()
    data object ShowCitySelectErrorDialog: WeatherEffect()
}