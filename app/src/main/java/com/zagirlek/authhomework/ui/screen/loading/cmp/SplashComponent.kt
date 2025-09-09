package com.zagirlek.authhomework.ui.screen.loading.cmp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SplashComponent(
    componentContext: ComponentContext,
    mainContext: CoroutineContext,
    private val toLogin: () -> Unit
): ComponentContext by componentContext {

    private val _state = MutableValue<SplashMutation>(initialValue = SplashMutation.Loading)
    val state: Value<SplashMutation> = _state

    private val scope = coroutineScope(mainContext + SupervisorJob())

    init {
        scope.launch {
            hasToken()
            _state.update { SplashMutation.OnFinish }
        }
    }

    private suspend fun hasToken(): Boolean {
        //...типо проверяем наличие токена в DataStore, если есть то в mainScreen, иначе на loginScreen
        delay(2000)
        return true
    }

    fun action(splashAction: SplashAction){
        when(splashAction){
            SplashAction.Finish -> toLogin()
        }
    }
}