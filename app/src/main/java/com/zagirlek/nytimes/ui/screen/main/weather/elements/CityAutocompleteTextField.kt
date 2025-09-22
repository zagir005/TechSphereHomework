package com.zagirlek.nytimes.ui.screen.main.weather.elements

import android.content.res.Configuration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zagirlek.nytimes.R
import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.ui.elements.AppTextField
import com.zagirlek.nytimes.ui.theme.NyTimesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityAutocompleteTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    selectedCity: City? = null,
    lastVariants: List<City> = emptyList(),
    autocompleteVariants: List<City> = emptyList(),
    errorMessage: String? = null,
    onCustomCitySelected: (String) -> Unit = { },
    onCitySelected: (City) -> Unit = { }
) {
    val interactionSource = remember { MutableInteractionSource() }
    var isFocused by remember { mutableStateOf(false) }
    var isCityPickerVisible by remember { mutableStateOf(isFocused && value.isNotBlank()) }

    Column(modifier = modifier.fillMaxWidth()) {
        AppTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                isCityPickerVisible = true
            },
            singleLine = true,
            label = stringResource(R.string.city),
            errorMessage = errorMessage,
            textStyle = if (selectedCity != null) LocalTextStyle.current.copy(color = Color.Yellow) else LocalTextStyle.current,
            interactionSource = interactionSource,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                    isCityPickerVisible = focusState.isFocused
                }
        )

        if (isCityPickerVisible) {
            Surface(
                tonalElevation = 4.dp,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 4.dp)
            ) {
                CityList(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 200.dp),
                    customCityName = value,
                    onCustomCityClick = {
                        onCustomCitySelected(it)
                        isCityPickerVisible = false
                    },
                    onCityClick = {
                        onCitySelected(it)
                        isCityPickerVisible = false
                    },
                    lastVariants = lastVariants,
                    autocompleteVariants = autocompleteVariants
                )
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun DropdownTextFieldDefaultPreview() {
    NyTimesTheme {
        Surface {
            var value by remember { mutableStateOf("") }

            val savedCities = listOf(City(0, "Moscow"), City(0, "Махачкала"))
            val autocompleteVariants = listOf(
                City(0, "Moscow"), City(0, "Makhachkala"),
                City(0, "Moscow"), City(0, "Makhachkala")
            )

            CityAutocompleteTextField(
                value = value,
                onValueChange = { value = it },
                lastVariants = savedCities.filter { it.name.contains(value) },
                autocompleteVariants = autocompleteVariants.filter { it.name.contains(value) }
            )
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DropdownTextFieldNightPreview() {
    NyTimesTheme {
        Surface {
            var value by remember { mutableStateOf("") }

            val savedCities = listOf(City(0, "Moscow"), City(0, "Махачкала"))
            val autocompleteVariants = listOf(
                City(0, "Moscow"), City(0, "Makhachkala"),
                City(0, "Moscow"), City(0, "Makhachkala")
            )

            CityAutocompleteTextField(
                value = value,
                onValueChange = { value = it },
                lastVariants = savedCities.filter { it.name.contains(value) },
                autocompleteVariants = autocompleteVariants.filter { it.name.contains(value) }
            )
        }
    }
}