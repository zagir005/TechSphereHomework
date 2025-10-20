package com.zagirlek.weather.cmp.state

import com.zagirlek.common.basemvi.reducer.ViewState
import com.zagirlek.weather.cmp.state.textfield.CityTextFieldState
import com.zagirlek.weather.cmp.state.textfield.DegreeTextFieldState
import com.zagirlek.weather.model.WeatherPoint

data class WeatherState(
    val cityTextFieldState: CityTextFieldState = CityTextFieldState(),
    val temperatureTextFieldState: DegreeTextFieldState = DegreeTextFieldState(),
    val weatherPointsHistory: List<WeatherPoint> = listOf(),
    val weatherPointsHistoryLoading: Boolean = true,
    val lastWeatherPoint: WeatherPoint? = null
): ViewState