package com.zagirlek.nytimes.ui.main.weather.cmp.state

import com.zagirlek.nytimes.core.base.reducer.ViewState
import com.zagirlek.nytimes.domain.model.WeatherPoint
import com.zagirlek.nytimes.ui.main.weather.cmp.state.textfield.CityTextFieldState
import com.zagirlek.nytimes.ui.main.weather.cmp.state.textfield.DegreeTextFieldState

data class WeatherState(
    val cityTextFieldState: CityTextFieldState = CityTextFieldState(),
    val temperatureTextFieldState: DegreeTextFieldState = DegreeTextFieldState(),
    val weatherPointsHistory: List<WeatherPoint> = listOf(),
    val weatherPointsHistoryLoading: Boolean = true,
    val lastWeatherPoint: WeatherPoint? = null
): ViewState