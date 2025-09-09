package com.zagirlek.authhomework.ui.screen.loading.cmp

sealed class SplashMutation{
    object Loading: SplashMutation()

    object OnFinish: SplashMutation()
}