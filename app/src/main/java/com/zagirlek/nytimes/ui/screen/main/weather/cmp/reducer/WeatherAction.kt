package com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer

import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.domain.model.WeatherPoint

sealed class WeatherAction {
    data class CityFieldAddCity(val city: City): WeatherAction()

    data class WeatherPointHistoryLoaded(val list: List<WeatherPoint>): WeatherAction()

    data class SubmitWeatherPoint(val weatherPoint: WeatherPoint): WeatherAction()

    data class CityFieldAutocompleteVariantLoaded(val autocompleteVariants: List<City>): WeatherAction()

    data class CityFieldLastVariantsLoaded(val lastVariants: List<City>): WeatherAction()

    data object ReloadWeatherPointFields: WeatherAction()

    data class DeleteWeatherPoint(val id: Long): WeatherAction()

    data class CityFieldValueChanged(
        val value: String
    ): WeatherAction()

    data class CityFieldVariantPick(val variant: City): WeatherAction()

    data class DegreeFieldValueChanged(val value: Int): WeatherAction()



}