package com.zagirlek.auth.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.zagirlek.auth.elements.textfield.TextFieldState
import com.zagirlek.auth.elements.textfield.textfielderror.LoginTextFieldError
import com.zagirlek.auth.elements.textfield.textfielderror.PasswordTextFieldError
import com.zagirlek.auth.usecase.AuthUseCase
import com.zagirlek.auth.usecase.AuthWithoutLoginUseCase
import com.zagirlek.common.error.AuthError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class AuthStoreFactory(
    private val storeFactory: StoreFactory,
    private val authUseCase: AuthUseCase,
    private val authWithoutLoginUseCase: AuthWithoutLoginUseCase
) {
    private sealed interface Msg {
        data class LoginFieldValue(
            val text: String,
            val error: LoginTextFieldError?
        ): Msg
        data class PasswordFieldValue(
            val text: String,
            val error: PasswordTextFieldError?
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
                            error = validateLogin(intent.text)
                        )
                    )
                    dispatch(
                        Msg.AuthAvailability(
                            isAvailable = isAuthAvailable(
                                loginTextFieldState = state().loginTextFieldState,
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
                                loginTextFieldState = state().loginTextFieldState,
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
                    publish(AuthStore.Label.ToMain)
                }
            }

        private fun auth(login: String, password: String){
            dispatch(Msg.Loading(true))
            scope.launch {
                withContext(Dispatchers.IO){ authUseCase(login, password) }
                    .onSuccess {
                        dispatch(Msg.Loading(false))
                        publish(AuthStore.Label.ToMain)
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
            loginTextFieldState: TextFieldState<LoginTextFieldError>,
            passwordTextFieldState: TextFieldState<PasswordTextFieldError>,
        ): Boolean {
            return loginTextFieldState.isNotEmpty() && passwordTextFieldState.isNotEmpty()
                    && loginTextFieldState.isValid() && passwordTextFieldState.isValid()
        }

        private fun validateLogin(login: String): LoginTextFieldError? {
            return when {
                login.isEmpty() -> null
                !login.matches(Regex("^[^A-Za-z]+$")) -> LoginTextFieldError.OnlyCyrillic
                else -> null
            }
        }

        private fun validatePassword(password: String): PasswordTextFieldError? {
            return when {
                password.isEmpty() -> null
                password.length < 6 -> PasswordTextFieldError.LengthLessThenSix
                password.length > 12 -> PasswordTextFieldError.LengthMoreThenTwelve
                password.toCharArray().none { it.isDigit() } -> PasswordTextFieldError.WithoutNumber
                password.toCharArray().none { it.isLetter() } -> PasswordTextFieldError.WithoutLetter
                else -> null
            }
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