package com.zagirlek.nytimes.ui.main.weather.elements

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zagirlek.nytimes.R
import com.zagirlek.nytimes.core.utils.withCelsius
import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.domain.model.WeatherPoint
import com.zagirlek.nytimes.core.ui.elements.AppChip
import com.zagirlek.nytimes.ui.theme.NyTimesTheme

@Composable
fun WeatherPointInfo(
    weatherPoint: WeatherPoint,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppChip(
            text = weatherPoint.city.name.uppercase()
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(R.drawable.ic_thermostat),
            contentDescription = stringResource(R.string.icon)
        )
        Text(
            text = "${weatherPoint.temperature} ".withCelsius()
        )
    }
}

@Preview(
    name = "Default"
)
@Preview(
    name = "Night mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun WeatherPointCardDefaultPreview() {
    NyTimesTheme {
        Surface {
            WeatherPointInfo(
                WeatherPoint(
                    City(id = 0, name = "Москва"),
                    temperature = 10,
                    id = 0
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

