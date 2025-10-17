package com.zagirlek.nytimes.ui.auth.di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.zagirlek.auth.usecase.AuthUseCase
import com.zagirlek.auth.usecase.AuthWithoutLoginUseCase
import com.zagirlek.nytimes.ui.auth.AuthScreenComponent

class AuthModule(
    private val storeFactory: StoreFactory,
    private val authUseCase: AuthUseCase,
    private val authWithoutLoginUseCase: AuthWithoutLoginUseCase
){
    fun getAuthComponent(
        componentContext: ComponentContext,
        toMain: () -> Unit
    ): AuthScreenComponent = AuthScreenComponent(
        componentContext = componentContext,
        storeFactory = storeFactory,
        authUseCase = authUseCase,
        authWithoutLoginUseCase = authWithoutLoginUseCase,
        toMain = toMain
    )
}