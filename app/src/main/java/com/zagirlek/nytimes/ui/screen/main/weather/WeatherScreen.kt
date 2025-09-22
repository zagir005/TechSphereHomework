package com.zagirlek.nytimes.ui.screen.main.weather

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.zagirlek.nytimes.ui.screen.main.weather.elements.WeatherScreenContent

@Composable
fun WeatherScreen(
    component: WeatherComponent,
) {
    val state by component.state.subscribeAsState()

    WeatherScreenContent(
        modifier = Modifier.padding(horizontal = 4.dp),
        state = state,
        sendAction = component::action,
    )
}

