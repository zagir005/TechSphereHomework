package com.zagirlek.nytimes.ui.screen.main.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.zagirlek.nytimes.R
import com.zagirlek.nytimes.ui.elements.AppTextField
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherUiAction
import com.zagirlek.nytimes.ui.screen.main.weather.elements.AutocompleteCityTextField
import com.zagirlek.nytimes.ui.screen.main.weather.elements.RatedWeatherCard

@Composable
fun WeatherUi(
    component: WeatherComponent
) {
    val state by component.state.subscribeAsState()

    Column {
        if(state.lastWeatherPoint != null)
            RatedWeatherCard(state.lastWeatherPoint!!)
        else{
            AutocompleteCityTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.cityTextFieldState.value,
                onValueChange = { component.action(WeatherUiAction.CityFieldValueChanged(it)) },
                lastVariants = state.cityTextFieldState.lastVariants,
                autocompleteVariants = state.cityTextFieldState.autocompleteVariants,
                onNewCity = { component.action(WeatherUiAction.AddCity(it)) },
                onVariantPick = { component.action(WeatherUiAction.CityFieldVariantPick(it)) }
            )

            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.temperatureTextFieldState.value.toString(),
                label = stringResource(R.string.temperature_with_celsius),
                onValueChange = {
                    component.action(
                        action = WeatherUiAction.TemperatureFieldValueChanged(
                            value = if (it.isDigitsOnly())
                                it.toIntOrNull() ?: 0
                            else if(it.isEmpty()){
                                0
                            }
                            else
                                state.temperatureTextFieldState.value
                        )
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }
    }
}

