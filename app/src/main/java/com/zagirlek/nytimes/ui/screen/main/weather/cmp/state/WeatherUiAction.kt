package com.zagirlek.nytimes.ui.screen.main.weather.cmp.state

import com.zagirlek.nytimes.domain.model.City

sealed class WeatherUiAction{
    data class AddCity(val cityName: String): WeatherUiAction()

    data class AddWeatherPoint(val city: City, val degree: Int): WeatherUiAction()

    data object ReloadWeatherPointFields: WeatherUiAction()

    data class DeleteWeatherPoint(val id: Long): WeatherUiAction()

    data class CityFieldValueChanged(val value: String): WeatherUiAction()

    data class CityFieldVariantPick(val variant: City): WeatherUiAction()

    data class DegreeFieldValueChanged(val value: Int): WeatherUiAction()
}
