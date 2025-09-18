package com.zagirlek.nytimes.ui.screen.main.weather.cmp

import com.arkivanov.decompose.ComponentContext
import com.zagirlek.nytimes.ui.screen.main.weather.WeatherComponent

class DefaultWeatherComponent(
    componentContext: ComponentContext
): ComponentContext by componentContext, WeatherComponent