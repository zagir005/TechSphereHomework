package com.zagirlek.splash

import com.arkivanov.decompose.value.Value
import com.zagirlek.splash.cmp.state.SplashAction
import com.zagirlek.splash.cmp.state.SplashState

interface SplashComponent {
    val state: Value<SplashState>
    fun action(splashAction: SplashAction)
}