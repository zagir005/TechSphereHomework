package com.zagirlek.nytimes.ui.screen.main.weather.cmp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.domain.model.WeatherPoint
import com.zagirlek.nytimes.domain.repository.CityAutocompleteRepository
import com.zagirlek.nytimes.domain.repository.CityRepository
import com.zagirlek.nytimes.domain.repository.WeatherRepository
import com.zagirlek.nytimes.ui.screen.main.weather.WeatherComponent
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer.WeatherAction
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer.WeatherReducer
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherState
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherUiAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class DefaultWeatherComponent(
    componentContext: ComponentContext,
    private val cityRepository: CityRepository,
    private val weatherRepository: WeatherRepository,
    private val autocompleteRepository: CityAutocompleteRepository
): ComponentContext by componentContext, WeatherComponent{
    private val _state: MutableValue<WeatherState> = MutableValue(WeatherState())
    override val state: Value<WeatherState> = _state

    private val reducer = WeatherReducer()
    private val scope = coroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        scope.launch {
            weatherRepository.getWeatherPoints().collect { list ->
                _state.update {
                    reducer.reduce(
                        state = it,
                        action = WeatherAction.WeatherPointHistoryLoaded(list)
                    )
                }
            }
        }
    }

    override fun action(action: WeatherUiAction) {
        with(reducer){
            when(action){
                is WeatherUiAction.AddCity -> {
                    scope.launch {
                        val city = addCity(action.cityName)
                        _state.update {
                            reduce(
                                state = it,
                                action = WeatherAction.CityFieldAddCity(
                                    city
                                )
                            )
                        }
                    }
                }
                is WeatherUiAction.AddWeatherPoint -> {
                    scope.launch {
                        val weatherPoint = addWeatherPoint(action.city, action.degree)
                        _state.update {
                            reduce(
                                state = it,
                                action = WeatherAction.SubmitWeatherPoint(weatherPoint)
                            )
                        }
                    }
                }
                is WeatherUiAction.CityFieldValueChanged ->
                    scope.launch {
                        val value = action.value
                        _state.update {
                            reduce(
                                state = it,
                                action = WeatherAction.CityFieldValueChanged(
                                    value = value
                                )
                            )
                        }

                        val lastVariants = getLastVariants(value)
                        _state.update {
                            reduce(
                                state = it,
                                action = WeatherAction.CityFieldLastVariantsLoaded(
                                    lastVariants
                                )
                            )
                        }

                        val autocompleteVariants = getAutocompleteVariants(value)
                        _state.update {
                            reduce(
                                state = it,
                                action = WeatherAction.CityFieldAutocompleteVariantLoaded(
                                    autocompleteVariants
                                )
                            )
                        }
                    }
                is WeatherUiAction.CityFieldVariantPick -> {
                    _state.update {
                        reduce(
                            state = it,
                            action = WeatherAction.CityFieldVariantPick(action.variant)
                        )
                    }
                }
                is WeatherUiAction.TemperatureFieldValueChanged -> {
                    _state.update {
                        reduce(
                            state = it,
                            action = WeatherAction.DegreeFieldValueChanged(value = action.value)
                        )
                    }
                }
                is WeatherUiAction.DeleteWeatherPoint -> {

                }
                WeatherUiAction.ReloadWeatherPointFields -> {
                    _state.update {
                        reduce(
                            state = it,
                            action = WeatherAction.ReloadWeatherPointFields
                        )
                    }
                }
            }
        }
    }

    private suspend fun addCity(name: String): City {
        val id = cityRepository.saveCity(name)
        return cityRepository.getCityById(id)!!
    }

    private suspend fun addWeatherPoint(city: City, degree: Int): WeatherPoint{
        val id = weatherRepository.addWeatherPoint(city,degree)
        return weatherRepository.getWeatherPointById(id)
    }

    private suspend fun getAutocompleteVariants(filter: String): List<City>{
        autocompleteRepository.autocompleteSearch(filter)
            .onSuccess { return it }
        return emptyList()
    }

    private suspend fun getLastVariants(filter: String): List<City>{
        return cityRepository.findCity(filter)
    }

}