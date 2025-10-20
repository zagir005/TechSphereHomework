package com.zagirlek.weather

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.zagirlek.ui.R
import com.zagirlek.weather.cmp.state.WeatherEffect
import com.zagirlek.weather.elements.WeatherScreenContent
import com.zagirlek.weather.elements.weatherdialog.WeatherScreenDialog
import com.zagirlek.weather.elements.weatherdialog.WeatherScreenDialogState

@Composable
fun WeatherScreenUi(
    component: WeatherScreen,
) {
    val context = LocalContext.current
    
    val state by component.state.subscribeAsState()
    val effect = component.effect
    var dialogState by remember { mutableStateOf<WeatherScreenDialogState?>(null) }

    LaunchedEffect(effect) {
        effect.collect {
            dialogState = when(it){
                WeatherEffect.ShowCitySaveErrorErrorDialog -> WeatherScreenDialogState(
                    title = context.getString(R.string.error),
                    message = context.getString(R.string.city_save_error)
                ) 
                WeatherEffect.ShowCitySelectErrorDialog -> WeatherScreenDialogState(
                    title = context.getString(R.string.error),
                    message = context.getString(R.string.city_select_error)
                )
                WeatherEffect.ShowWeatherPointAddErrorDialog -> WeatherScreenDialogState(
                    title = context.getString(R.string.error),
                    message = context.getString(R.string.weather_point_add_error)
                )
            }
        }
    }

    dialogState?.let {
        WeatherScreenDialog(
            state = it,
            onDismiss = {
                dialogState = null
            }
        )
    }

    WeatherScreenContent(
        modifier = Modifier.padding(horizontal = 4.dp),
        state = state,
        sendAction = component::action,
    )
}



