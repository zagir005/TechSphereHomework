package com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer

import com.zagirlek.nytimes.core.base.reducer.Reducer
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherState

class WeatherReducer: Reducer<WeatherState, WeatherMutation> {
    override fun reduce(state: WeatherState, mutation: WeatherMutation): WeatherState {
        return when(mutation){
            is WeatherMutation.CityField.ValueChanged -> state.copy(
                cityTextFieldState = state.cityTextFieldState.copy(
                    value = mutation.value,
                    errorMessage = null,
                    selectedCity = null
                )
            )
            is WeatherMutation.CityField.VariantPick -> state.copy(
                cityTextFieldState = state.cityTextFieldState.copy(
                    selectedCity = mutation.variant,
                    value = mutation.variant.name
                )
            )
            is WeatherMutation.CityField.SaveCity -> state.copy(
                cityTextFieldState = state.cityTextFieldState.copy(
                    selectedCity = mutation.city,
                    value = mutation.city.name
                )
            )
            is WeatherMutation.CityField.AutocompleteVariantsLoaded -> state.copy(
                cityTextFieldState = state.cityTextFieldState.copy(
                    autocompleteVariants = mutation.autocompleteVariants
                )
            )
            is WeatherMutation.CityField.LastVariantsLoaded -> state.copy(
                cityTextFieldState = state.cityTextFieldState.copy(
                    lastVariants = mutation.lastVariants
                )
            )
            is WeatherMutation.DegreeFieldValueChanged -> state.copy(
                temperatureTextFieldState = state.temperatureTextFieldState.copy(
                    value = mutation.value
                )
            )
            is WeatherMutation.AddWeatherPoint -> state.copy(
                lastWeatherPoint = mutation.weatherPoint
            )
            WeatherMutation.ReloadWeatherPointFields -> state.copy(
                lastWeatherPoint = null
            )

            is WeatherMutation.WeatherPointHistoryLoaded -> state.copy(
                weatherPointsHistory = mutation.list
            )
        }
    }
}