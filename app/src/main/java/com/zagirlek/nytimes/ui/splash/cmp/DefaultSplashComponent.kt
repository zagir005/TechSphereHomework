package com.zagirlek.nytimes.ui.splash.cmp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.zagirlek.auth.model.AuthToken
import com.zagirlek.auth.usecase.GetCurrentAuthTokenUseCase
import com.zagirlek.nytimes.ui.splash.SplashComponent
import com.zagirlek.nytimes.ui.splash.cmp.state.SplashAction
import com.zagirlek.nytimes.ui.splash.cmp.state.SplashState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class DefaultSplashComponent(
    componentContext: ComponentContext,
    private val onAuthRequired: () -> Unit,
    private val onAuthSuccess: () -> Unit,
    private val getCurrentAuthTokenUseCase: GetCurrentAuthTokenUseCase
): ComponentContext by componentContext, SplashComponent {

    private val _state: MutableValue<SplashState> = MutableValue(initialValue = SplashState(isLoading = true))
    override val state: Value<SplashState> get() = _state
    private val scope = coroutineScope(Dispatchers.IO + SupervisorJob())
    private var token: AuthToken? = null

    init {
        scope.launch {
            token = getCurrentAuthTokenUseCase()
            _state.update {
                it.copy(isLoading = false)
            }
        }
    }

    override fun action(splashAction: SplashAction){
        when(splashAction){
            SplashAction.SplashFinished -> if (token != null) onAuthSuccess() else onAuthRequired()
        }
    }
}