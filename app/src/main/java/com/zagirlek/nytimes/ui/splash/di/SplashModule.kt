package com.zagirlek.nytimes.ui.splash.di

import com.arkivanov.decompose.ComponentContext
import com.zagirlek.nytimes.domain.usecase.auth.GetCurrentAuthTokenUseCase
import com.zagirlek.nytimes.ui.splash.SplashComponent
import com.zagirlek.nytimes.ui.splash.cmp.DefaultSplashComponent

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