package com.zagirlek.nytimes.ui.screen.splash.cmp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.zagirlek.nytimes.domain.model.AuthToken
import com.zagirlek.nytimes.domain.repository.AuthRepository
import com.zagirlek.nytimes.ui.screen.splash.SplashComponent
import com.zagirlek.nytimes.ui.screen.splash.cmp.state.SplashAction
import com.zagirlek.nytimes.ui.screen.splash.cmp.state.SplashEffect
import com.zagirlek.nytimes.ui.screen.splash.cmp.state.SplashState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class DefaultSplashComponent(
    componentContext: ComponentContext,
    private val onAuthRequired: () -> Unit,
    private val onAuthSuccess: () -> Unit,
    private val authRepository: AuthRepository
): ComponentContext by componentContext, SplashComponent {

    private val _state = MutableValue<SplashState>(initialValue = SplashState.Loading)
    override val state: Value<SplashState> = _state

    private val _effect = MutableSharedFlow<SplashEffect>()
    override val effect: SharedFlow<SplashEffect> = _effect.asSharedFlow()

    private val scope = coroutineScope(Dispatchers.IO + SupervisorJob())

    private var token: AuthToken? = null

    init {
        scope.launch {
            token = authRepository.getCurrToken()
            _effect.emit(SplashEffect.FinishSplash)
        }
    }

    override fun action(splashAction: SplashAction){
        when(splashAction){
            SplashAction.SplashFinished -> if (token != null) onAuthSuccess() else onAuthRequired()
        }
    }
}