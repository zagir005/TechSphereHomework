package com.zagirlek.nytimes.ui.screen.main.weather.cmp.state

import com.zagirlek.nytimes.core.base.component.Action
import com.zagirlek.nytimes.domain.model.City

sealed class WeatherAction: Action{

    sealed class CityField{
        data class SaveCity(val cityName: String): WeatherAction()

        data class ValueChanged(val value: String): WeatherAction()

        data class VariantPick(val variant: City): WeatherAction()
    }
    data class AddWeatherPoint(val city: City, val degree: Int): WeatherAction()

    data object ReloadWeatherPointFields: WeatherAction()

    data class DeleteWeatherPoint(val id: Long): WeatherAction()
    data class TemperatureFieldValueChanged(val value: Int): WeatherAction()
}
