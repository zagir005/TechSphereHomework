package com.zagirlek.nytimes.ui.screen.main.weather.cmp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.zagirlek.nytimes.core.base.component.BaseComponent
import com.zagirlek.nytimes.core.utils.canceledJob
import com.zagirlek.nytimes.domain.usecase.AddWeatherPointUseCase
import com.zagirlek.nytimes.domain.usecase.DeleteWeatherPointUseCase
import com.zagirlek.nytimes.domain.usecase.GetCityAutocompleteUseCase
import com.zagirlek.nytimes.domain.usecase.GetOrPutCityUseCase
import com.zagirlek.nytimes.domain.usecase.GetRecentCityListUseCase
import com.zagirlek.nytimes.domain.usecase.GetWeatherPointsHistoryFlowUseCase
import com.zagirlek.nytimes.ui.screen.main.weather.WeatherComponent
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer.WeatherMutation
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer.WeatherMutation.AddWeatherPoint
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer.WeatherMutation.CityField.AutocompleteVariantsError
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer.WeatherMutation.CityField.AutocompleteVariantsLoaded
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer.WeatherMutation.CityField.LastVariantsLoaded
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer.WeatherMutation.CityField.SaveCity
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer.WeatherMutation.CityField.ValueChanged
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer.WeatherMutation.CityField.VariantPick
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer.WeatherMutation.DegreeFieldValueChanged
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer.WeatherMutation.WeatherPointHistoryLoaded
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.reducer.WeatherReducer
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherAction
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherEffect
import com.zagirlek.nytimes.ui.screen.main.weather.cmp.state.WeatherState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
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

    private val _effect: MutableSharedFlow<WeatherEffect> = MutableSharedFlow(extraBufferCapacity = 1, replay = 0)
    override val effect: SharedFlow<WeatherEffect> get() = _effect

    private var cityJob by canceledJob()

    init {
        componentScope.launch {
            getWeatherPointsHistoryFlowUseCase().collect { list ->
                _state.update {
                    WeatherPointHistoryLoaded(list).reduce(it)
                }
            }
        }
    }

    override fun action(action: WeatherAction) {
        when (action) {
            is WeatherAction.CityField.SaveCity -> {
                componentScope.launch {
                    getOrPutCityUseCase(action.cityName)
                        .onSuccess { city ->
                            _state.update {
                                SaveCity(city).reduce(it)
                            }
                        }
                        .onFailure { error ->
                            _effect.emit(WeatherEffect.ShowCitySaveErrorErrorDialog)
                        }
                }
            }
            is WeatherAction.AddWeatherPoint -> {
                componentScope.launch {
                    addWeatherPointUseCase(action.city, action.degree)
                        .onSuccess { weatherPoint ->
                            _state.update {
                                AddWeatherPoint(weatherPoint).reduce(it)
                            }
                        }
                        .onFailure { error ->
                            _effect.emit(WeatherEffect.ShowWeatherPointAddErrorDialog)
                        }
                }
            }
            is WeatherAction.CityField.ValueChanged -> {
                _state.update {
                    ValueChanged(action.value).reduce(it)
                }

                if (action.value.isNotBlank()){
                    cityJob = componentScope.launch {
                        val lastVariants = getRecentCityListUseCase(action.value)
                        _state.update {
                            LastVariantsLoaded(lastVariants).reduce(it)
                        }
                        delay(200)
                        getCityAutocompleteUseCase(action.value)
                            .onSuccess { cityList ->
                                _state.update {
                                    AutocompleteVariantsLoaded(cityList).reduce(it)
                                }
                            }
                            .onFailure { error ->
                                _state.update {
                                    AutocompleteVariantsError("Проверьте интернет-соединение!").reduce(it)
                                }
                            }
                    }
                }
            }
            is WeatherAction.CityField.RecentCitySelected -> {
                _state.update {
                    VariantPick(action.selectedCity).reduce(it)
                }
            }
            is WeatherAction.CityField.AutocompleteCitySelected -> {
                componentScope.launch {
                    getOrPutCityUseCase(action.name)
                        .onSuccess { city ->
                            _state.update {
                                VariantPick(city).reduce(it)
                            }
                        }
                        .onFailure { error ->
                            _effect.emit(WeatherEffect.ShowCitySelectErrorDialog)
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
                    deleteWeatherPointUseCase(action.id)
                }
            }
            WeatherAction.ReloadWeatherPointFields -> {
                _state.update {
                    WeatherMutation.ReloadWeatherPointFields.reduce(it)
                }
            }
        }
    }

    private class StateHolder : InstanceKeeper.Instance {
        val state: MutableValue<WeatherState> = MutableValue(WeatherState())
    }

    private object HolderKey
}