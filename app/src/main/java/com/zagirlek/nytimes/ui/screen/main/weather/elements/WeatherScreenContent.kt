package com.zagirlek.nytimes.ui.screen.main.weather.elements

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zagirlek.nytimes.R
import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.domain.model.WeatherPoint
import com.zagirlek.nytimes.ui.elements.AppButton
import com.zagirlek.nytimes.ui.elements.AppTextField
import com.zagirlek.nytimes.ui.elements.NyTimesPreview
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherAction
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherState

@Composable
fun WeatherScreenContent(
    state: WeatherState,
    sendAction: (WeatherAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        if(state.lastWeatherPoint != null) {
            RatedWeatherCard(state.lastWeatherPoint)
        } else {
            CityAutocompleteTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.cityTextFieldState.value,
                selectedCity = state.cityTextFieldState.selectedCity,
                autocompleteVariants = state.cityTextFieldState.autocompleteVariants,
                autocompleteVariantsLoading = state.cityTextFieldState.autocompleteVariantsLoading,
                autocompleteVariantsErrorMessage = state.cityTextFieldState.autocompleteVariantsError,
                recentVariants = state.cityTextFieldState.lastVariants,
                recentVariantsLoading = state.cityTextFieldState.autocompleteVariantsLoading,
                onValueChange = {
                    sendAction(WeatherAction.CityField.ValueChanged(it))
                },
                onCustomCitySelected = {
                    sendAction(WeatherAction.CityField.SaveCity(it))
                },
                onAutocompleteCitySelected = {
                    sendAction(WeatherAction.CityField.AutocompleteCitySelected(it.name))
                },
                onLoadedCitySelected = {
                    sendAction(WeatherAction.CityField.RecentCitySelected(it))
                }
            )

            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.temperatureTextFieldState.value.toString(),
                label = stringResource(R.string.temperature_with_celsius),
                onValueChange = {
                    sendAction(
                        WeatherAction.TemperatureFieldValueChanged(
                            value = it.toIntOrNull() ?: state.temperatureTextFieldState.value
                        )
                    )
                }
            )
        }

        AppButton(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            onClick = {
                if (state.lastWeatherPoint == null){
                    sendAction(
                        WeatherAction.AddWeatherPoint(
                        city = state.cityTextFieldState.selectedCity!!,
                        degree = state.temperatureTextFieldState.value
                    ))
                }else{
                    sendAction(WeatherAction.ReloadWeatherPointFields)
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

        Text(
            text = stringResource(R.string.your_last_search)
        )

        if (state.weatherPointsHistoryLoading)
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth()
            )

        if (state.weatherPointsHistory.isEmpty())
            EmptyHistoryAttention()
        else
            WeatherPointsHistoryList(
                list = state.weatherPointsHistory
            ){
                sendAction(WeatherAction.DeleteWeatherPoint(it.id))
            }


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

@Preview(
    showSystemUi = true
)
@Preview(
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun WeatherUiDayPreview() {
    NyTimesPreview {
        WeatherScreenContent(
            state = WeatherState(),
            sendAction = {}
        )
    }
}

@Preview(
    showSystemUi = true
)
@Preview(
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun WeatherUiLastWeatherPointDefaultPreview() {
    NyTimesPreview {
        WeatherScreenContent(
            state = WeatherState(
                lastWeatherPoint = WeatherPoint(
                    city = City(0, "Москва"),
                    temperature = 0,
                    id = 0
                )
            ),
            sendAction = {}
        )
    }
}