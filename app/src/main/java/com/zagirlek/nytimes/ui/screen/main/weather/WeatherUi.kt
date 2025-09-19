package com.zagirlek.nytimes.ui.screen.main.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.zagirlek.nytimes.R
import com.zagirlek.nytimes.ui.elements.AppButton
import com.zagirlek.nytimes.ui.elements.AppTextField
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherUiAction
import com.zagirlek.nytimes.ui.screen.main.weather.elements.AutocompleteCityTextField
import com.zagirlek.nytimes.ui.screen.main.weather.elements.RatedWeatherCard
import com.zagirlek.nytimes.ui.screen.main.weather.elements.WeatherPointInfo

@Composable
fun WeatherUi(
    component: WeatherComponent
) {
    val state by component.state.subscribeAsState()

    Column(
        Modifier.padding(horizontal = 4.dp)
    ) {
        if(state.lastWeatherPoint != null)
            RatedWeatherCard(state.lastWeatherPoint!!)
        else{
            AutocompleteCityTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.cityTextFieldState.value,
                onValueChange = { component.action(WeatherUiAction.CityFieldValueChanged(it)) },
                lastVariants = state.cityTextFieldState.lastVariants,
                autocompleteVariants = state.cityTextFieldState.autocompleteVariants,
                lastVariantsLoading = state.cityTextFieldState.lastVariantsLoading,
                autocompleteVariantsLoading = state.cityTextFieldState.autocompleteVariantsLoading,
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
                            value = it.toIntOrNull() ?: state.temperatureTextFieldState.value
                        )
                    )
                }
            )
        }

        Row {
            Spacer(
                modifier = Modifier.weight(1f)
            )
            AppButton(
                onClick = {
                    if (state.lastWeatherPoint == null){
                        component.action(WeatherUiAction.AddWeatherPoint(
                            city = state.cityTextFieldState.selectedCity!!,
                            degree = state.temperatureTextFieldState.value
                        ))
                    }else{
                        component.action(WeatherUiAction.ReloadWeatherPointFields)
                    }
                },
                enabled = state.cityTextFieldState.selectedCity != null
            ) {
                Text(
                    text = if (state.lastWeatherPoint == null)
                        stringResource(R.string.rate).uppercase()
                    else
                        stringResource(R.string.create_new_query).uppercase()
                )
            }
        }

        Text(
            text = stringResource(R.string.your_last_search)
        )

        LazyColumn {
            if (state.weatherPointsHistory.isEmpty())
                item {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.your_previous_queries_will_appear_here),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            else
                items(state.weatherPointsHistory) {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(4.dp)
                    ) {
                        WeatherPointInfo(
                            weatherPoint = it,
                            modifier = Modifier.padding(14.dp)
                        )
                    }
                }
        }
    }
}

