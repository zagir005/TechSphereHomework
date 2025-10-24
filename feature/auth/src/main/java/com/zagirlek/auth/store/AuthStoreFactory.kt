package com.zagirlek.auth.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.zagirlek.auth.usecase.AuthUseCase
import com.zagirlek.auth.usecase.AuthWithoutLoginUseCase
import com.zagirlek.common.error.AuthError
import com.zagirlek.common.model.AuthToken
import com.zagirlek.common.textfieldstate.AppTextFieldState
import com.zagirlek.common.validation.nickname.NicknameError
import com.zagirlek.common.validation.nickname.validateNickname
import com.zagirlek.common.validation.password.PasswordError
import com.zagirlek.common.validation.password.validatePassword
import kotlinx.coroutines.launch

internal class AuthStoreFactory(
    private val storeFactory: StoreFactory,
    private val authUseCase: AuthUseCase,
    private val authWithoutLoginUseCase: AuthWithoutLoginUseCase
) {
    private sealed interface Msg {
        data class LoginFieldValue(
            val text: String,
            val error: NicknameError.NicknameValidationError?
        ): Msg
        data class PasswordFieldValue(
            val text: String,
            val error: PasswordError.PasswordValidationError?
        ): Msg
        data class Loading(val isLoading: Boolean): Msg
        data class AuthAvailability(val isAvailable: Boolean): Msg
    }

    fun create(): AuthStore =
        object: AuthStore, Store<AuthStore.Intent, AuthStore.State, AuthStore.Label> by storeFactory.create(
            name = "AuthStore",
            initialState = AuthStore.State(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ){}

    private inner class ExecutorImpl: CoroutineExecutor<AuthStore.Intent, Nothing, AuthStore.State, Msg, AuthStore.Label>(){
        override fun executeIntent(intent: AuthStore.Intent) =
            when(intent) {
                is AuthStore.Intent.LoginTextFieldChange -> {
                    dispatch(
                        Msg.LoginFieldValue(
                            text = intent.text,
                            error = validateNickname(intent.text)
                        )
                    )
                    dispatch(
                        Msg.AuthAvailability(
                            isAvailable = isAuthAvailable(
                                nicknameTextFieldState = state().loginTextFieldState,
                                passwordTextFieldState = state().passwordTextFieldState
                            )
                        )
                    )
                }
                is AuthStore.Intent.PasswordTextFieldChange -> {
                    dispatch(
                        Msg.PasswordFieldValue(
                            text = intent.text,
                            error = validatePassword(intent.text)
                        )
                    )
                    dispatch(
                        Msg.AuthAvailability(
                            isAuthAvailable(
                                nicknameTextFieldState = state().loginTextFieldState,
                                passwordTextFieldState = state().passwordTextFieldState
                            )
                        )
                    )
                }
                AuthStore.Intent.Auth -> auth(
                    login = state().loginTextFieldState.value,
                    password = state().passwordTextFieldState.value
                )
                AuthStore.Intent.AuthWithoutLogin -> {
                    scope.launch {
                        authWithoutLoginUseCase()
                    }
                    publish(AuthStore.Label.ToClient)
                }
            }

        private fun auth(login: String, password: String){
            dispatch(Msg.Loading(true))
            scope.launch {
                authUseCase(login, password)
                    .onSuccess {
                        publish(
                            label = when(it.tokenType){
                                AuthToken.TokenType.ADMIN -> AuthStore.Label.ToAdmin
                                else -> AuthStore.Label.ToClient
                            }
                        )
                        dispatch(Msg.Loading(false))
                    }
                    .onFailure { error ->
                        dispatch(Msg.Loading(false))
                        publish(
                            AuthStore.Label.ShowError(
                                error = when(error){
                                    is AuthError.ServerError -> AuthStore.Error.ServerError
                                    is AuthError.NoNetworkConnection -> AuthStore.Error.NoNetworkConnection
                                    is AuthError.InvalidCredentials -> AuthStore.Error.InvalidCredentials
                                    else -> AuthStore.Error.UnknownError
                                }
                            )
                        )
                    }
            }
        }

        private fun isAuthAvailable(
            nicknameTextFieldState: AppTextFieldState<NicknameError.NicknameValidationError>,
            passwordTextFieldState: AppTextFieldState<PasswordError.PasswordValidationError>,
        ): Boolean {
            return nicknameTextFieldState.isNotEmpty() && passwordTextFieldState.isNotEmpty()
                    && nicknameTextFieldState.isValid() && passwordTextFieldState.isValid()
        }
    }

    private object ReducerImpl: Reducer<AuthStore.State, Msg>{
        override fun AuthStore.State.reduce(msg: Msg): AuthStore.State =
            when(msg){
                is Msg.LoginFieldValue -> copy(
                    loginTextFieldState = loginTextFieldState.copy(
                        value = msg.text,
                        error = msg.error
                    ),
                    loading = false
                )
                is Msg.PasswordFieldValue -> copy(
                    passwordTextFieldState = passwordTextFieldState.copy(
                        value = msg.text,
                        error = msg.error,
                    ),
                    loading = false
                )
                is Msg.Loading -> copy(
                    loading = msg.isLoading
                )
                is Msg.AuthAvailability -> copy(
                    isAuthAvailable = msg.isAvailable
                )
            }
    }

}