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
import com.zagirlek.nytimes.domain.usecase.AddWeatherPointUseCase
import com.zagirlek.nytimes.domain.usecase.DeleteWeatherPointUseCase
import com.zagirlek.nytimes.domain.usecase.GetCityAutocompleteUseCase
import com.zagirlek.nytimes.domain.usecase.GetOrPutCityUseCase
import com.zagirlek.nytimes.domain.usecase.GetRecentCityListUseCase
import com.zagirlek.nytimes.domain.usecase.GetWeatherPointsHistoryFlowUseCase
import com.zagirlek.nytimes.ui.screen.main.weather.WeatherComponent
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer.WeatherMutation
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer.WeatherMutation.AddWeatherPoint
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer.WeatherMutation.CityField.AutocompleteVariantsLoaded
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer.WeatherMutation.CityField.LastVariantsLoaded
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer.WeatherMutation.CityField.SaveCity
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer.WeatherMutation.CityField.ValueChanged
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer.WeatherMutation.CityField.VariantPick
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer.WeatherMutation.DegreeFieldValueChanged
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer.WeatherReducer
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherAction
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DefaultWeatherComponent(
    componentContext: ComponentContext,
    private val getCityAutocompleteUseCase: GetCityAutocompleteUseCase,
    private val getWeatherPointsHistoryFlowUseCase: GetWeatherPointsHistoryFlowUseCase,
    private val getRecentCityListUseCase: GetRecentCityListUseCase,
    private val deleteWeatherPointUseCase: DeleteWeatherPointUseCase,
    private val addWeatherPointUseCase: AddWeatherPointUseCase,
    private val getOrPutCityUseCase: GetOrPutCityUseCase
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
            getWeatherPointsHistoryFlowUseCase().collect { list ->
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
                        SaveCity(city).reduce(it)
                    }
                }
            }
            is WeatherAction.AddWeatherPoint -> {
                componentScope.launch {
                    val weatherPoint = addWeatherPoint(action.city, action.degree)
                    _state.update {
                        AddWeatherPoint(weatherPoint).reduce(it)
                    }
                }
            }
            is WeatherAction.CityField.ValueChanged -> {
                _state.update {
                    ValueChanged(action.value).reduce(it)
                }

                cityJob?.cancel()
                cityJob = componentScope.launch {
                    val lastVariants = getRecentCityVariants(action.value)
                    _state.update {
                        LastVariantsLoaded(lastVariants).reduce(it)
                    }

                    val autocompleteVariants = getCityAutocompleteVariants(action.value)
                    _state.update {
                        AutocompleteVariantsLoaded(autocompleteVariants).reduce(it)
                    }
                }
            }

            is WeatherAction.CityField.LoadedCitySelected -> {
                _state.update {
                    VariantPick(action.selectedCity).reduce(it)
                }
            }
            is WeatherAction.CityField.AutocompleteCitySelected -> {
                componentScope.launch {
                    val selectedCity = saveCity(action.name)
                    _state.update {
                        VariantPick(selectedCity).reduce(it)
                    }
                }
            }
            is WeatherAction.TemperatureFieldValueChanged -> {
                _state.update {
                    DegreeFieldValueChanged(value = action.value).reduce(it)
                }
            }
            is WeatherAction.DeleteWeatherPoint -> {
                componentScope.launch {
                    deleteWeatherPointById(action.id)
                }
            }
            WeatherAction.ReloadWeatherPointFields -> {
                _state.update {
                    WeatherMutation.ReloadWeatherPointFields.reduce(it)
                }
            }

        }
    }

    private suspend fun saveCity(name: String): City =  getOrPutCityUseCase(name)

    private suspend fun getCityAutocompleteVariants(filter: String): List<City> {
        // TODO: Обработать onFailure кейс
        getCityAutocompleteUseCase(filter)
            .onSuccess { return it }
            .onFailure { throw it }
        return emptyList()
    }

    private suspend fun getRecentCityVariants(filter: String): List<City> {
        return getRecentCityListUseCase(filter)
    }

    private suspend fun addWeatherPoint(city: City, temperature: Int): WeatherPoint =
        addWeatherPointUseCase(city = city, temperature = temperature)

    private suspend fun deleteWeatherPointById(id: Long) = deleteWeatherPointUseCase(id)

    private class StateHolder : InstanceKeeper.Instance {
        val state: MutableValue<WeatherState> = MutableValue(WeatherState())
    }

    private object HolderKey
}