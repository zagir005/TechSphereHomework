package com.zagirlek.nytimes

import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.domain.model.WeatherPoint
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer.WeatherMutation
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer.WeatherReducer
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class WeatherReducerTest {
    private val reducer = WeatherReducer()
    private val state = WeatherState()
    private val exampleCity: City = City(id = 0, "Moscow")
    private val exampleWeatherPoint: WeatherPoint = WeatherPoint(
        city = exampleCity,
        temperature = 20,
        id = 0
    )

    @Test
    fun cityValueChanged() {
        // Проверяем смену текста и сброс selectedCity
        val result = reducer.reduce(state, WeatherMutation.CityField.ValueChanged("Moscow"))
        assertEquals("Moscow", result.cityTextFieldState.value)
        assertNull(result.cityTextFieldState.selectedCity)
    }

    @Test
    fun cityVariantPick() {
        // Проверяем выбор города из вариантов
        val result = reducer.reduce(
            state = state,
            mutation = WeatherMutation.CityField.VariantPick(exampleCity)
        )
        assertEquals(exampleCity, result.cityTextFieldState.selectedCity)
        assertEquals(exampleCity.name, result.cityTextFieldState.value)
    }

    @Test
    fun citySave() {
        // Проверяем сохранение выбранного города
        val city = City(0, "Moscow")
        val result = reducer.reduce(
            state = state,
            mutation = WeatherMutation.CityField.SaveCity(city)
        )
        assertEquals(city, result.cityTextFieldState.selectedCity)
        assertEquals("Moscow", result.cityTextFieldState.value)
    }

    @Test
    fun degreeChanged() {
        // Проверяем смену значения температуры
        val result = reducer.reduce(
            state = state,
            mutation = WeatherMutation.DegreeFieldValueChanged(25)
        )
        assertEquals(25, result.temperatureTextFieldState.value)
    }

    @Test
    fun historyLoaded() {
        // Проверяем загрузку WeatherPointHistory
        val list = List(5) {
            exampleWeatherPoint
        }
        val result = reducer.reduce(
            state = state,
            mutation = WeatherMutation.WeatherPointHistoryLoaded(list)
        )
        assertEquals(list, result.weatherPointsHistory)
    }
}
