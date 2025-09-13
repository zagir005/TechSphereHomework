package com.zagirlek.nytimes.ui.screen.splash.cmp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.zagirlek.nytimes.ui.screen.root.components.SplashComponent
import com.zagirlek.nytimes.ui.screen.splash.cmp.state.SplashAction
import com.zagirlek.nytimes.ui.screen.splash.cmp.state.SplashMutation
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SplashComponentImpl(
    componentContext: ComponentContext,
    mainContext: CoroutineContext,
    private val toLogin: () -> Unit
): ComponentContext by componentContext, SplashComponent {

    private val _state = MutableValue<SplashMutation>(initialValue = SplashMutation.Loading)
    override val state: Value<SplashMutation> = _state

    private val scope = coroutineScope(mainContext + SupervisorJob())

    init {
        scope.launch {
            hasToken()
            _state.update { SplashMutation.OnFinish }
        }
    }

    private suspend fun hasToken(): Boolean {
        delay(2000)
        return true
    }

    override fun action(splashAction: SplashAction){
        when(splashAction){
            SplashAction.Finish -> toLogin()
        }
    }
}