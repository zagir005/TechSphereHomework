package com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer

import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.domain.model.WeatherPoint

sealed class WeatherAction {

    sealed class CityFieldAction{
        data class SaveCity(val city: City): WeatherAction()

        data class AutocompleteVariantsLoaded(val autocompleteVariants: List<City>): WeatherAction()

        data class LastVariantsLoaded(val lastVariants: List<City>): WeatherAction()

        data class ValueChanged(val value: String): WeatherAction()

        data class VariantPick(val variant: City): WeatherAction()
    }

    data class WeatherPointHistoryLoaded(val list: List<WeatherPoint>): WeatherAction()

    data class SubmitWeatherPoint(val weatherPoint: WeatherPoint): WeatherAction()

    data object ReloadWeatherPointFields: WeatherAction()

    data class DeleteWeatherPoint(val id: Long): WeatherAction()

    data class DegreeFieldValueChanged(val value: Int): WeatherAction()



}