package com.zagirlek.nytimes.ui.screen.main.weather.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zagirlek.nytimes.R
import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.ui.elements.AppTextField
import com.zagirlek.nytimes.ui.theme.NyTimesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutocompleteCityTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    lastVariants: List<City> = emptyList(),
    autocompleteVariants: List<City> = emptyList(),
    errorMessage: String? = null,
    lastVariantsLoading: Boolean = false,
    autocompleteVariantsLoading: Boolean = false,
    onNewCity: (String) -> Unit = { },
    onVariantPick: (City) -> Unit = { }
) {
    var isFocused by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        AppTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            label = stringResource(R.string.city),
            errorMessage = errorMessage,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                }
        )

        if (isFocused && value.isNotBlank()) {
            Surface(
                tonalElevation = 4.dp,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().height(200.dp)
                ) {
                    item {
                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = stringResource(R.string.save)
                                )
                            },
                            text = { Text("Сохранить город \"$value\"") },
                            onClick = {
                                onNewCity(value)
                                isFocused = false
                            }
                        )
                    }

                    if (lastVariantsLoading){
                        item {
                            CircularProgressIndicator()
                        }
                    }
                    if (lastVariants.isNotEmpty()) {
                        item {
                            Text(
                                stringResource(R.string.saved_cities),
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                        items(lastVariants) { city ->
                            DropdownMenuItem(
                                text = { Text(city.name) },
                                onClick = {
                                    onVariantPick(city)
                                    isFocused = false
                                }
                            )
                        }
                    }

                    if (lastVariants.isNotEmpty() && autocompleteVariants.isNotEmpty()) {
                        item { HorizontalDivider() }
                    }


                    if (autocompleteVariantsLoading)
                        item {
                            CircularProgressIndicator()
                        }

                    if (autocompleteVariants.isNotEmpty()) {
                        item {
                            Text(
                                text = stringResource(R.string.choose_city),
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                        items(autocompleteVariants) { city ->
                            DropdownMenuItem(
                                text = { Text(city.name) },
                                onClick = {
                                    onVariantPick(city)
                                    isFocused = false
                                }
                            )
                        }
                    }

                    if (lastVariants.isEmpty() && autocompleteVariants.isEmpty()) {
                        item {
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.not_found_anything)) },
                                onClick = {},
                                enabled = false
                            )
                        }
                    }
                }
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
        Surface(Modifier.padding(20.dp)) {
            var value by remember { mutableStateOf("") }

            val savedCities = listOf(City(0, "Москва"), City(0, "Махачкала"))
            val suggestions = listOf(
                City(0, "Москва"), City(0, "Махачкала"),
                City(0, "Москва"), City(0, "Махачкала"),
                City(0, "Москва"), City(0, "Махачкала"),
                City(0, "Москва"), City(0, "Махачкала"),
            )

            AutocompleteCityTextField(
                value = value,
                onValueChange = { value = it },
                lastVariants = savedCities.filter { it.name.contains(value) },
                autocompleteVariants = suggestions.filter { it.name.contains(value) }
            )
        }
    }
}