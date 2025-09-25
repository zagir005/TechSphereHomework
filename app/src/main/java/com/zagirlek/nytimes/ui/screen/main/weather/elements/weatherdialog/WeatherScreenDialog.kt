package com.zagirlek.nytimes.ui.screen.main.weather.elements.weatherdialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.zagirlek.nytimes.R

@Composable
fun WeatherScreenDialog (
    state: WeatherScreenDialogState,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = { onDismiss() }
            ){
                Text(text = stringResource(R.string.ok))
            }
        },
        title = { Text(text = state.title) },
        text = { Text(text = state.message) },
        modifier = modifier
    )
}