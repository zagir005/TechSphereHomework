package com.zagirlek.nytimes.ui.splash

import com.arkivanov.decompose.value.Value
import com.zagirlek.nytimes.ui.splash.cmp.state.SplashAction
import com.zagirlek.nytimes.ui.splash.cmp.state.SplashState

interface SplashComponent {
    val state: Value<SplashState>
    fun action(splashAction: SplashAction)
}