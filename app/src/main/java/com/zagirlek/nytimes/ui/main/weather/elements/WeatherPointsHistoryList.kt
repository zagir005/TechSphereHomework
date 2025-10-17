package com.zagirlek.nytimes.ui.main.weather.elements

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxDefaults
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zagirlek.nytimes.R
import com.zagirlek.ui.elements.NyTimesPreview
import com.zagirlek.weather.model.City
import com.zagirlek.weather.model.WeatherPoint

@Composable
fun WeatherPointsHistoryList(
    list: List<WeatherPoint>,
    modifier: Modifier = Modifier,
    onItemDelete: (WeatherPoint) -> Unit = {},
) {

    LazyColumn(
        modifier = modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = list,
            key = { it.id }
        ) { item ->
            WeatherPointListItem(
                weatherPoint = item,
                onDelete = onItemDelete,
                modifier = Modifier.animateItem()
            )
        }
    }
}

@Composable
private fun WeatherPointListItem(
    weatherPoint: WeatherPoint,
    onDelete: (WeatherPoint) -> Unit,
    modifier: Modifier = Modifier,
) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
            SwipeToDismissBoxValue.Settled,
            SwipeToDismissBoxDefaults.positionalThreshold
        )

    LaunchedEffect(swipeToDismissBoxState.currentValue) {
        if (swipeToDismissBoxState.currentValue == SwipeToDismissBoxValue.EndToStart) {
            onDelete(weatherPoint)
        }
    }

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        enableDismissFromStartToEnd = false,
        modifier = modifier.fillMaxWidth(),
        backgroundContent = {
            if (swipeToDismissBoxState.dismissDirection == SwipeToDismissBoxValue.EndToStart){
                Icon(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = stringResource(R.string.delete_weather_point_item),
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Red, shape = RoundedCornerShape(8.dp))
                        .wrapContentSize(Alignment.CenterEnd)
                        .padding(8.dp)
                )
            }
        }
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(8.dp)
        ) {
            WeatherPointInfo(
                weatherPoint = weatherPoint,
                modifier = Modifier.padding(14.dp)
            )
        }
    }
}

@Preview
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun WeatherPointsHistoryListDefaultPreview() {
    NyTimesPreview {
        Surface {
            Box{
                WeatherPointsHistoryList(
                    list = List(10){
                        WeatherPoint(
                            city = City(0, "Moscow"),
                            temperature = 10,
                            id = 0
                        )
                    },
                    onItemDelete = {}
                )
            }
        }
    }
}


