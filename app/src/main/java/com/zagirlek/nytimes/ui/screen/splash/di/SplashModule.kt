package com.zagirlek.nytimes.ui.screen.splash.di

import com.arkivanov.decompose.ComponentContext
import com.zagirlek.nytimes.domain.usecase.GetCurrentAuthTokenUseCase
import com.zagirlek.nytimes.ui.screen.splash.SplashComponent
import com.zagirlek.nytimes.ui.screen.splash.cmp.DefaultSplashComponent

class SplashModule(
    private val getCurrentAuthTokenUseCase: GetCurrentAuthTokenUseCase
) {
    fun getSplashComponent(
        componentContext: ComponentContext,
        toAuth: () -> Unit,
        toMain: () -> Unit,
    ): SplashComponent = DefaultSplashComponent(
        componentContext = componentContext,
        onAuthRequired = toAuth,
        onAuthSuccess = toMain,
        getCurrentAuthTokenUseCase = getCurrentAuthTokenUseCase
    )
}