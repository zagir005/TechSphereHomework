package com.zagirlek.nytimes.ui.screen.main.weather.cmp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.zagirlek.nytimes.core.base.component.BaseComponent
import com.zagirlek.nytimes.domain.model.City
import com.zagirlek.nytimes.domain.model.WeatherPoint
import com.zagirlek.nytimes.domain.repository.CityAutocompleteRepository
import com.zagirlek.nytimes.domain.repository.CityRepository
import com.zagirlek.nytimes.domain.repository.WeatherRepository
import com.zagirlek.nytimes.ui.screen.main.weather.WeatherComponent
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer.WeatherMutation
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer.WeatherReducer
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherAction
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DefaultWeatherComponent(
    componentContext: ComponentContext,
    private val cityRepository: CityRepository,
    private val weatherRepository: WeatherRepository,
    private val autocompleteRepository: CityAutocompleteRepository
) : BaseComponent<WeatherState, WeatherAction, WeatherMutation, WeatherReducer>(
    componentContext = componentContext,
    reducer = WeatherReducer()
), WeatherComponent {
    private val stateHolder = instanceKeeper.getOrCreate(HolderKey) { StateHolder() }
    private val _state get() = stateHolder.state
    override val state: Value<WeatherState> get() = stateHolder.state

    private var cityJob: Job? = null

    init {
        componentScope.launch {
            weatherRepository.getWeatherPoints().collect { list ->
                _state.update {
                    WeatherMutation.WeatherPointHistoryLoaded(list).reduce(_state.value)
                }
            }
        }
    }

    override fun action(action: WeatherAction) {
        when (action) {
            is WeatherAction.CityField.SaveCity -> {
                componentScope.launch {
                    val city = saveCity(action.cityName)
                    _state.update {
                        WeatherMutation.CityField.SaveCity(city).reduce(it)
                    }
                }
            }

            is WeatherAction.AddWeatherPoint -> {
                componentScope.launch {
                    val weatherPoint = addWeatherPoint(action.city, action.degree)
                    _state.update {
                        WeatherMutation.AddWeatherPoint(weatherPoint).reduce(it)
                    }
                }
            }
            is WeatherAction.CityField.ValueChanged -> {
                _state.update {
                    WeatherMutation.CityField.ValueChanged(action.value).reduce(it)
                }

                cityJob?.cancel()
                cityJob = componentScope.launch {
                    val lastVariants = getLastCityVariants(action.value)
                    _state.update {
                        WeatherMutation.CityField.LastVariantsLoaded(lastVariants).reduce(it)
                    }

                    val autocompleteVariants = getCityAutocompleteVariants(action.value)
                    _state.update {
                        WeatherMutation.CityField.AutocompleteVariantsLoaded(autocompleteVariants).reduce(it)
                    }
                }
            }

            is WeatherAction.CityField.VariantPick -> {
                componentScope.launch {
                    val pickedCity = cityRepository.getCityById(action.variant.id) ?: saveCity(
                        name = action.variant.name
                    )
                    _state.update {
                        WeatherMutation.CityField.VariantPick(pickedCity).reduce(it)
                    }
                }
            }

            is WeatherAction.TemperatureFieldValueChanged -> {
                _state.update {
                    WeatherMutation.DegreeFieldValueChanged(value = action.value).reduce(it)
                }
            }

            is WeatherAction.DeleteWeatherPoint -> {

            }

            WeatherAction.ReloadWeatherPointFields -> {
                _state.update {
                    WeatherMutation.ReloadWeatherPointFields.reduce(it)
                }
            }
        }
    }

    private suspend fun saveCity(name: String): City {
        val id = cityRepository.saveCity(name)
        return cityRepository.getCityById(id)!!
    }

    private suspend fun addWeatherPoint(city: City, degree: Int): WeatherPoint {
        val id = weatherRepository.addWeatherPoint(city, degree)
        return weatherRepository.getWeatherPointById(id)
    }

    private suspend fun getCityAutocompleteVariants(filter: String): List<City> {
        autocompleteRepository.autocompleteSearch(filter)
            .onSuccess { return it }
        return emptyList()
    }

    private suspend fun getLastCityVariants(filter: String): List<City> {
        return cityRepository.findCity(filter)
    }

    private class StateHolder : InstanceKeeper.Instance {
        val state: MutableValue<WeatherState> = MutableValue(WeatherState())
    }

    private object HolderKey

}