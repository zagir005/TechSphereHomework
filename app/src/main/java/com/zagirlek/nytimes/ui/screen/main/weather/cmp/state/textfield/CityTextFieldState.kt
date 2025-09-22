package com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.textfield

import com.zagirlek.nytimes.domain.model.City

data class CityTextFieldState(
    val value: String = "",
    val selectedCity: City? = null,
    val autocompleteVariants: List<City> = emptyList(),
    val lastVariants: List<City> = emptyList(),
    val errorMessage: Int? = null
)