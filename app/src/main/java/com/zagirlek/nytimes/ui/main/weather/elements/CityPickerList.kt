package com.zagirlek.nytimes.ui.main.weather.elements

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zagirlek.nytimes.R
import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.ui.theme.NyTimesTheme

@Composable
fun CityPickerList(
    modifier: Modifier = Modifier,
    customCityName: String = "",
    recentVariants: List<City> = emptyList(),
    recentVariantsLoading: Boolean = false,
    autocompleteVariants: List<City> = emptyList(),
    autocompleteVariantsLoading: Boolean = false,
    autocompleteVariantsErrorMessage: String? = null,
    onCustomCityClick: (String) -> Unit = { },
    onLoadedCityClick: (City) -> Unit = { },
    onAutocompleteCityClick: (City) -> Unit = { }
) {
    val isLoading = autocompleteVariantsLoading && recentVariantsLoading
    val isEmpty = recentVariants.isEmpty() && autocompleteVariants.isEmpty()

    LazyColumn(
        modifier = modifier
    ) {
        if (customCityName.isNotBlank()){
            item {
                CityListItem(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.save)
                        )
                    },
                    text = "Сохранить город \"$customCityName\"",
                    onClick = { onCustomCityClick(customCityName) }
                )
                Spacer(
                    modifier = Modifier.height(4.dp)
                )
                HorizontalDivider()
            }
        }


        if (recentVariants.isNotEmpty()) {
            item {
                Text(
                    stringResource(R.string.saved_cities),
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            items(recentVariants) { city ->
                CityListItem(
                    text = city.name,
                    onClick = {
                        onLoadedCityClick(city)
                    }
                )
            }
        }
        item { HorizontalDivider() }

        if (!isEmpty)
            item {
                Text(
                    text = stringResource(R.string.city_search),
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

        if(recentVariantsLoading && autocompleteVariantsLoading)
            loadingItem()

        if (recentVariantsLoading && !autocompleteVariantsLoading)
            loadingItem()


        if (isEmpty && !isLoading && autocompleteVariantsErrorMessage == null) {
            item {
                CityListItem(
                    text = stringResource(R.string.not_found_anything),
                    onClick = {}
                )
            }
        }

        if (autocompleteVariantsErrorMessage != null)
            item {
                CityListItem(
                    text = autocompleteVariantsErrorMessage,
                    onClick = {}
                )
            }

        if (autocompleteVariantsLoading && !recentVariantsLoading)
            loadingItem()

        if (autocompleteVariants.isNotEmpty()) {
            items(autocompleteVariants) { city ->
                CityListItem(
                    text = city.name,
                    onClick = { onAutocompleteCityClick(city) }
                )
            }
        }
    }
}

private fun LazyListScope.loadingItem(modifier: Modifier = Modifier) {
    item {
        CircularProgressIndicator(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth()
        )
    }
}

@Composable
private fun CityListItem(
    text: String,
    onClick: () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (leadingIcon != null) {
            leadingIcon()
            Spacer(Modifier.width(8.dp))
        }
        Text(text, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview
@Composable
private fun CityListDefaultPreview() {
    val savedCities = listOf(City(0, "Moscow"), City(0, "Махачкала"))
    val autocompleteVariants = listOf(
        City(0, "Moscow"), City(0, "Makhachkala"),
        City(0, "Moscow"), City(0, "Makhachkala"),
    )

    NyTimesTheme {
        Surface{
            CityPickerList(
                customCityName = "Mos",
                onCustomCityClick = {},
                onLoadedCityClick = {},
                autocompleteVariants = autocompleteVariants,
                recentVariants = savedCities
            )
        }
    }
}



@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun CityListNightPreview() {
    val savedCities = listOf(City(0, "Moscow"), City(0, "Махачкала"))
    val autocompleteVariants = listOf(
        City(0, "Moscow"), City(0, "Makhachkala"),
        City(0, "Moscow"), City(0, "Makhachkala")
    )

    NyTimesTheme {
        Surface{
            CityPickerList(
                customCityName = "Mos",
                onCustomCityClick = {},
                onLoadedCityClick = {},
                autocompleteVariants = autocompleteVariants,
                recentVariants = savedCities
            )
        }
    }
}


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun CityListNightLoadingPreview() {
    val savedCities = listOf(City(0, "Moscow"), City(0, "Махачкала"))
    val autocompleteVariants = listOf(
        City(0, "Moscow"), City(0, "Makhachkala"),
        City(0, "Moscow"), City(0, "Makhachkala")
    )

    NyTimesTheme {
        Surface{
            CityPickerList(
                customCityName = "Mos",
                onCustomCityClick = {},
                onLoadedCityClick = {},
                autocompleteVariantsLoading = true,
                recentVariantsLoading = true,
            )
        }
    }
}