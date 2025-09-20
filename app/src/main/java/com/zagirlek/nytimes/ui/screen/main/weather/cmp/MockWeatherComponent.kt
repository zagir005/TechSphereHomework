package com.zagirlek.nytimes.ui.screen.main.weather.cmp

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.domain.model.WeatherPoint
import com.zagirlek.nytimes.ui.screen.main.weather.WeatherComponent
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherState
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherUiAction

class MockWeatherComponent(
    weatherState: WeatherState = WeatherState()
): WeatherComponent {
    override val state: Value<WeatherState> = MutableValue(weatherState)

    override fun action(action: WeatherUiAction) {}

    companion object{
        fun getCity(
            name: String = "Москва"
        ) = City(name = name, id = 0)

        fun getCityList(
            size: Int = 2,
            city: City = getCity()
        ) = List(size){ city }
        fun getWeatherPoint(
            city: City = getCity(),
            temperature: Int = 10
        ) = WeatherPoint(
            city = city,
            temperature = 10,
            id = 0
        )
        fun getWeatherPointList(
            size: Int = 3,
            weatherPoint: WeatherPoint = getWeatherPoint()
        ) = List(size){ weatherPoint }
    }
}