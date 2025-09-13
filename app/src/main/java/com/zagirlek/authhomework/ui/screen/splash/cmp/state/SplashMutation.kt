package com.zagirlek.authhomework.ui.screen.splash.cmp.state

sealed class SplashMutation{
    object Loading: SplashMutation()

    object OnFinish: SplashMutation()
}