package com.zagirlek.nytimes.ui.main.weather.elements

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zagirlek.ui.theme.NyTimesTheme
import com.zagirlek.weather.model.City
import com.zagirlek.weather.model.WeatherPoint

@Composable
fun RatedWeatherCard(
    weatherPoint: WeatherPoint,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(12.dp)
        ) {
            WeatherPointInfo(
                weatherPoint = weatherPoint
            )

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )

            Text(
                text = describeTemperature(
                    city = weatherPoint.city.name,
                    temperature = weatherPoint.temperature
                ),
                style = MaterialTheme.typography.titleMedium
            )

        }
    }
}

fun describeTemperature(city: String, temperature: Int): String {
    val description = when {
        temperature < -20 -> "очень холодно"
        temperature in -20..-1 -> "холодно"
        temperature in 0..10 -> "прохладно"
        temperature in 11..20 -> "комфортно"
        temperature in 21..30 -> "тепло"
        else -> "жарко"
    }
    return "Сейчас в г.$city $description"
}



@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview
@Composable
private fun RatedWeatherCardDefaultPreview() {
    NyTimesTheme {
        Surface{
            RatedWeatherCard(
                WeatherPoint(
                    City(id = 0, name = "Москва"),
                    temperature = 10,
                    id = 0
                )
            )
        }
    }
}