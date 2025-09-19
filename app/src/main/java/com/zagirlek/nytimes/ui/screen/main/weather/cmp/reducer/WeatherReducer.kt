package com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer

import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherState

class WeatherReducer {
    fun reduce(state: WeatherState, action: WeatherAction): WeatherState {
        return when(action){
            is WeatherAction.CityFieldValueChanged -> state.copy(
                cityTextFieldState = state.cityTextFieldState.copy(
                    value = action.value,
                    errorMessage = null,
                    selectedCity = null,
                    autocompleteVariantsLoading = true,
                    lastVariantsLoading = true
                )
            )
            is WeatherAction.CityFieldVariantPick -> state.copy(
                cityTextFieldState = state.cityTextFieldState.copy(
                    selectedCity = action.variant,
                    value = action.variant.name
                )
            )
            is WeatherAction.CityFieldAddCity -> state.copy(
                cityTextFieldState = state.cityTextFieldState.copy(
                    selectedCity = action.city,
                    value = action.city.name
                )
            )
            is WeatherAction.CityFieldAutocompleteVariantLoaded -> state.copy(
                cityTextFieldState = state.cityTextFieldState.copy(
                    autocompleteVariantsLoading = false,
                    autocompleteVariants = action.autocompleteVariants
                )
            )
            is WeatherAction.CityFieldLastVariantsLoaded -> state.copy(
                cityTextFieldState = state.cityTextFieldState.copy(
                    lastVariantsLoading = false,
                    lastVariants = action.lastVariants
                )
            )

            is WeatherAction.DegreeFieldValueChanged -> state.copy(
                temperatureTextFieldState = state.temperatureTextFieldState.copy(
                    value = action.value
                )
            )

            is WeatherAction.SubmitWeatherPoint -> state.copy(
                lastWeatherPoint = action.weatherPoint
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