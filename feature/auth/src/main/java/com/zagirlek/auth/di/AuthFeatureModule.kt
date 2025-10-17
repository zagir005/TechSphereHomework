package com.zagirlek.auth.di


import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.zagirlek.auth.AuthScreen
import com.zagirlek.auth.AuthScreenComponent
import com.zagirlek.auth.usecase.AuthUseCase
import com.zagirlek.auth.usecase.AuthWithoutLoginUseCase

class AuthFeatureModule(
    private val storeFactory: StoreFactory,
    private val authUseCase: AuthUseCase,
    private val authWithoutLoginUseCase: AuthWithoutLoginUseCase
){
    fun getAuthComponent(
        componentContext: ComponentContext,
        toMain: () -> Unit
    ): AuthScreen = AuthScreenComponent(
        componentContext = componentContext,
        storeFactory = storeFactory,
        authUseCase = authUseCase,
        authWithoutLoginUseCase = authWithoutLoginUseCase,
        toMain = toMain
    )
}