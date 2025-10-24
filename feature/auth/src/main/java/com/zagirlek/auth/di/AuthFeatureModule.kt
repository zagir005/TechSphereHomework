package com.zagirlek.auth.di


import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.zagirlek.auth.AuthScreen
import com.zagirlek.auth.AuthScreenComponent
import com.zagirlek.auth.usecase.AuthUseCase
import com.zagirlek.auth.usecase.AuthWithoutLoginUseCase
import com.zagirlek.auth.usecase.LogoutUseCase

class AuthFeatureModule(
    private val storeFactory: StoreFactory,
    private val authUseCase: AuthUseCase,
    private val authWithoutLoginUseCase: AuthWithoutLoginUseCase,
    private val logoutUseCase: LogoutUseCase
){
    fun getAuthComponent(
        componentContext: ComponentContext,
        toClient: () -> Unit,
        toAdmin: () -> Unit
    ): AuthScreen = AuthScreenComponent(
        componentContext = componentContext,
        storeFactory = storeFactory,
        authUseCase = authUseCase,
        logoutUseCase = logoutUseCase,
        authWithoutLoginUseCase = authWithoutLoginUseCase,
        toClient = toClient,
        toAdmin = toAdmin
    )
}