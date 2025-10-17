package com.zagirlek.splash.di

import com.arkivanov.decompose.ComponentContext
import com.zagirlek.auth.usecase.GetCurrentAuthTokenUseCase
import com.zagirlek.splash.SplashComponent
import com.zagirlek.splash.cmp.DefaultSplashComponent

class SplashFeatureModule(
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