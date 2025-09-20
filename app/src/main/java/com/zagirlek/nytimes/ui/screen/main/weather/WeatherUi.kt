package com.zagirlek.nytimes.ui.screen.main.weather

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.zagirlek.nytimes.R
import com.zagirlek.nytimes.domain.model.WeatherPoint
import com.zagirlek.nytimes.ui.elements.AppButton
import com.zagirlek.nytimes.ui.elements.AppTextField
import com.zagirlek.nytimes.ui.elements.NyTimesPreview
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.MockWeatherComponent
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherState
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

        if (state.weatherPointsHistory.isEmpty())
            EmptyHistoryAttention()
        else
            WeatherPointsHistoryList(
                list = state.weatherPointsHistory
            )
    }
}

@Composable
fun EmptyHistoryAttention(modifier: Modifier = Modifier) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.padding(4.dp)
    ) {
        Text(
            text = stringResource(R.string.your_previous_queries_will_appear_here),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun WeatherPointsHistoryList(
    list: List<WeatherPoint>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(list) {
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                shape = RoundedCornerShape(8.dp)
            ) {
                WeatherPointInfo(
                    weatherPoint = it,
                    modifier = Modifier.padding(14.dp)
                )
            }
        }
    }
}


@Preview(
    showSystemUi = true
)
@Composable
private fun WeatherUiDefaultPreview() {
    NyTimesPreview {
        WeatherUi(MockWeatherComponent(
            WeatherState(
                weatherPointsHistory = MockWeatherComponent.getWeatherPointList()
            )
        ))
    }
}

@Preview(
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun WeatherUiNightPreview() {
    NyTimesPreview {
        WeatherUi(MockWeatherComponent(
            WeatherState(
                weatherPointsHistory = MockWeatherComponent.getWeatherPointList()
            )
        ))
    }
}

@Preview(
    showSystemUi = true
)
@Composable
private fun WeatherUiLastWeatherPointDefaultPreview() {
    NyTimesPreview {
        WeatherUi(MockWeatherComponent(
            WeatherState(
                weatherPointsHistory = MockWeatherComponent.getWeatherPointList(),
                lastWeatherPoint = MockWeatherComponent.getWeatherPoint()
            )
        ))
    }
}

@Preview(
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun WeatherUiLastWeatherPointNightPreview() {
    NyTimesPreview {
        WeatherUi(MockWeatherComponent(
            WeatherState(
                weatherPointsHistory = MockWeatherComponent.getWeatherPointList(),
                lastWeatherPoint = MockWeatherComponent.getWeatherPoint()
            )
        ))
    }
}

