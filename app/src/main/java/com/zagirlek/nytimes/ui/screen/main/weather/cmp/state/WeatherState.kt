package com.zagirlek.nytimes.ui.screen.main.weather.cmp.state

import com.zagirlek.nytimes.domain.model.WeatherPoint
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.textfield.CityTextFieldState
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.textfield.DegreeTextFieldState

data class WeatherState(
    val cityTextFieldState: CityTextFieldState = CityTextFieldState(),
    val degreeTextFieldState: DegreeTextFieldState = DegreeTextFieldState(),
    val weatherPointsHistory: List<WeatherPoint> = listOf(),
    val lastWeatherPoint: WeatherPoint? = null
)