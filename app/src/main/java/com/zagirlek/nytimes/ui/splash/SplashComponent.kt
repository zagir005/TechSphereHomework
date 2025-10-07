package com.zagirlek.nytimes.ui.screen.splash

import com.arkivanov.decompose.value.Value
import com.zagirlek.nytimes.ui.screen.splash.cmp.state.SplashAction
import com.zagirlek.nytimes.ui.screen.splash.cmp.state.SplashState

interface SplashComponent {
    val state: Value<SplashState>
    fun action(splashAction: SplashAction)
}