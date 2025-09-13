package com.zagirlek.nytimes.ui.screen.root.components

import com.arkivanov.decompose.value.Value
import com.zagirlek.nytimes.ui.screen.splash.cmp.state.SplashAction
import com.zagirlek.nytimes.ui.screen.splash.cmp.state.SplashMutation

interface SplashComponent {

    val state: Value<SplashMutation>

    fun action(splashAction: SplashAction)
}