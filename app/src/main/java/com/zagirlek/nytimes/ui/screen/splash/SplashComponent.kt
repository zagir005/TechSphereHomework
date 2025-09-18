package com.zagirlek.nytimes.ui.screen.splash

import com.arkivanov.decompose.value.Value
import com.zagirlek.nytimes.ui.screen.splash.cmp.state.SplashAction
import com.zagirlek.nytimes.ui.screen.splash.cmp.state.SplashEffect
import com.zagirlek.nytimes.ui.screen.splash.cmp.state.SplashState
import kotlinx.coroutines.flow.SharedFlow

interface SplashComponent {

    val state: Value<SplashState>
    val effect: SharedFlow<SplashEffect>

    fun action(splashAction: SplashAction)
}