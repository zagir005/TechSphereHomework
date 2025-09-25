package com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.textfield

import com.zagirlek.nytimes.domain.model.City

data class CityTextFieldState(
    val value: String = "",
    val selectedCity: City? = null,
    val autocompleteVariants: List<City> = emptyList(),
    val autocompleteVariantsLoading: Boolean = false,
    val autocompleteVariantsError: String? = null,
    val lastVariants: List<City> = emptyList(),
    val lastVariantsLoading: Boolean = false,
)