package com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer

import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherState

class WeatherReducer {
    fun reduce(state: WeatherState, action: WeatherAction): WeatherState {
        return when(action){
            is WeatherAction.AddCity -> state.copy(
                cityTextFieldState = state.cityTextFieldState.copy(
                    selectedCity = action.city,
                    value = action.city.name
                )
            )
            is WeatherAction.AddWeatherPoint -> state.copy(
                lastWeatherPoint = action.weatherPoint
            )
            is WeatherAction.CityFieldValueChanged -> state.copy(
                cityTextFieldState = state.cityTextFieldState.copy(
                    value = action.value,
                    autocompleteVariants = action.lastVariants,
                    lastVariants = action.lastVariants
                )
            )
            is WeatherAction.CityFieldVariantPick -> state.copy(
                cityTextFieldState = state.cityTextFieldState.copy(
                    selectedCity = action.variant,
                    value = action.variant.name
                )
            )
            is WeatherAction.DegreeFieldValueChanged -> state.copy(
                degreeTextFieldState = state.degreeTextFieldState.copy(
                    value = action.value
                )
            )
            is WeatherAction.DeleteWeatherPoint -> state
            WeatherAction.ReloadWeatherPointFields -> state.copy(
                lastWeatherPoint = null
            )
            is WeatherAction.WeatherPointHistoryLoaded -> state.copy(
                weatherPointsHistory = action.list
            )
        }
    }
}