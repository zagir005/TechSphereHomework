package com.zagirlek.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.router.pages.Pages
import com.arkivanov.decompose.router.pages.PagesNavigation
import com.arkivanov.decompose.router.pages.childPages
import com.arkivanov.decompose.router.pages.select
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.zagirlek.auth.usecase.GetCurrentUserUseCase
import com.zagirlek.favorite.FavoriteNewsScreen
import com.zagirlek.favorite.di.FavoriteNewsFeatureModule
import com.zagirlek.latest.LatestNewsScreen
import com.zagirlek.latest.di.LatestNewsFeatureModule
import com.zagirlek.root.model.SessionModel
import com.zagirlek.root.model.toSessionModel
import com.zagirlek.weather.WeatherScreen
import com.zagirlek.weather.di.WeatherFeatureModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

internal class DefaultClientRootComponent(
    private val componentContext: ComponentContext,
    private val weatherFeatureModule: WeatherFeatureModule,
    private val latestNewsFeatureModule: LatestNewsFeatureModule,
    private val favoriteNewsFeatureModule: FavoriteNewsFeatureModule,
    private val getCurrUserUseCase: GetCurrentUserUseCase,
    private val onLogout: () -> Unit
): ComponentContext by componentContext, ClientRootComponent {
    private val scope = coroutineScope(Dispatchers.IO + SupervisorJob())
    private val navigation = PagesNavigation<Config>()
    override val pages: Value<ChildPages<Config, ClientRootComponent.Child>> = childPages(
        source = navigation,
        serializer = Config.serializer(),
        initialPages = {
            Pages(
                items = listOf(
                    Config.Weather,
                    Config.News,
                    Config.Favorites
                ),
                selectedIndex = 0
            )
        },
        childFactory = ::child,
    )
    private val _model: MutableStateFlow<SessionModel?> = MutableStateFlow(null)
    override val model: StateFlow<SessionModel?> = _model.asStateFlow()

    init {
        scope.launch {
            _model.update {
                getCurrUserUseCase()?.toSessionModel() ?: SessionModel(
                    nickname = "",
                    isGuests = true
                )
            }
        }
    }

    override fun selectPage(index: Int) {
         navigation.select(index)
    }

    override fun logout() = onLogout()

    private fun child(config: Config, component: ComponentContext): ClientRootComponent.Child {
        return when(config){
            Config.Favorites -> ClientRootComponent.Child.Favorites(component = favorites(componentContext = component))
            Config.News -> ClientRootComponent.Child.News(component = latest(componentContext = component))
            Config.Weather -> ClientRootComponent.Child.Weather(component = weather(componentContext = component))
        }
    }
    private fun weather(componentContext: ComponentContext): WeatherScreen =
        weatherFeatureModule.getWeatherComponent(componentContext = componentContext)
    private fun latest(componentContext: ComponentContext): LatestNewsScreen =
        latestNewsFeatureModule.getLatestNewsComponent(
            componentContext = componentContext,
        )
    private fun favorites(componentContext: ComponentContext): FavoriteNewsScreen =
        favoriteNewsFeatureModule.getFavoriteNewsComponent(
            componentContext = componentContext,
        )

    @Serializable
    sealed class Config{
        @Serializable
        object Weather: Config()
        @Serializable
        object Favorites: Config()
        @Serializable
        object News: Config()
    }
}