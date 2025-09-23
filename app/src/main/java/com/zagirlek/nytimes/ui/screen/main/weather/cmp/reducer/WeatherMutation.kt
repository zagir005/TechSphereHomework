package com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer

import com.zagirlek.nytimes.core.base.reducer.Mutation
import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.domain.model.WeatherPoint

sealed class WeatherMutation: Mutation {
    sealed class CityField: WeatherMutation(){
        data class SaveCity(val city: City): CityField()
        data class AutocompleteVariantsLoaded(val autocompleteVariants: List<City>): CityField()
        data class LastVariantsLoaded(val lastVariants: List<City>): CityField()
        data class ValueChanged(val value: String): CityField()
        data class VariantPick(val variant: City): CityField()
    }
    data class WeatherPointHistoryLoaded(val list: List<WeatherPoint>): WeatherMutation()
    data class AddWeatherPoint(val weatherPoint: WeatherPoint): WeatherMutation()
    data object ReloadWeatherPointFields: WeatherMutation()
    data class DegreeFieldValueChanged(val value: Int): WeatherMutation()
}