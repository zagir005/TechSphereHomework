package com.zagirlek.nytimes.ui.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.zagirlek.common.utils.getStore
import com.zagirlek.nytimes.domain.usecase.auth.AuthUseCase
import com.zagirlek.nytimes.domain.usecase.auth.AuthWithoutLoginUseCase
import com.zagirlek.nytimes.ui.auth.model.AuthModel
import com.zagirlek.nytimes.ui.auth.model.AuthSideEffect
import com.zagirlek.nytimes.ui.auth.model.toModel
import com.zagirlek.nytimes.ui.auth.store.AuthStore
import com.zagirlek.nytimes.ui.auth.store.AuthStoreFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn

class AuthScreenComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val authUseCase: AuthUseCase,
    private val authWithoutLoginUseCase: AuthWithoutLoginUseCase,
    private val toMain: () -> Unit
): AuthScreen, ComponentContext by componentContext {
    private val storeInstance = instanceKeeper.getStore{
        AuthStoreFactory(
            storeFactory = storeFactory,
            authUseCase = authUseCase,
            authWithoutLoginUseCase = authWithoutLoginUseCase
        ).create()
    }
    private val componentScope = coroutineScope(
        context = SupervisorJob() + Dispatchers.Main.immediate
    ).also {
        doOnDestroy { it.cancel() }
    }

    override val model: StateFlow<AuthModel> = storeInstance
        .stateFlow(lifecycle)
        .map {
            it.toModel()
        }
        .stateIn(
            scope = componentScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AuthModel()
        )

    override val sideEffect: SharedFlow<AuthSideEffect> = storeInstance
        .labels
        .map {
            when(it){
                is AuthStore.Label.ShowError -> AuthSideEffect.ShowErrorDialog(it.toModel())
                AuthStore.Label.ToMain -> {
                    toMain()
                    return@map null
                }
            }
        }
        .filterNotNull()
        .shareIn(
            scope = componentScope,
            started = SharingStarted.WhileSubscribed(),
            replay = 0
        )

    override fun loginFieldValueChanged(value: String) =
        storeInstance.accept(AuthStore.Intent.LoginTextFieldChange(value))

    override fun passwordFieldValueChanged(value: String) =
        storeInstance.accept(AuthStore.Intent.PasswordTextFieldChange(value))


    override fun onAuth() =
        storeInstance.accept(AuthStore.Intent.Auth)

    override fun continueWithoutAuth() =
        storeInstance.accept(AuthStore.Intent.AuthWithoutLogin)

}