package com.zagirlek.nytimes.ui.screen.auth.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.zagirlek.nytimes.core.error.InvalidCredentialsError
import com.zagirlek.nytimes.core.error.NetworkError
import com.zagirlek.nytimes.core.error.ServerError
import com.zagirlek.nytimes.domain.usecase.AuthUseCase
import com.zagirlek.nytimes.domain.usecase.AuthWithoutLoginUseCase
import com.zagirlek.nytimes.ui.screen.auth.elements.textfield.TextFieldState
import com.zagirlek.nytimes.ui.screen.auth.elements.textfield.textfielderror.LoginTextFieldError
import com.zagirlek.nytimes.ui.screen.auth.elements.textfield.textfielderror.PasswordTextFieldError
import com.zagirlek.nytimes.ui.screen.auth.store.AuthStore.Intent
import com.zagirlek.nytimes.ui.screen.auth.store.AuthStore.Label
import com.zagirlek.nytimes.ui.screen.auth.store.AuthStore.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthStoreFactory(
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
        object: AuthStore, Store<Intent, State, Label> by storeFactory.create(
            name = "AuthStore",
            initialState = State(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ){}

    private inner class ExecutorImpl: CoroutineExecutor<Intent, Nothing, State, Msg, Label>(){
        override fun executeIntent(intent: Intent) =
            when(intent) {
                is Intent.LoginTextFieldChange -> {
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
                is Intent.PasswordTextFieldChange -> {
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
                Intent.Auth -> auth(
                    login = state().loginTextFieldState.value,
                    password = state().passwordTextFieldState.value
                )
                Intent.AuthWithoutLogin -> {
                    scope.launch {
                        authWithoutLoginUseCase()
                    }
                    publish(Label.ToMain)
                }
            }

        private fun auth(login: String, password: String){
            dispatch(Msg.Loading(true))
            scope.launch {
                withContext(Dispatchers.IO){ authUseCase(login, password) }
                    .onSuccess {
                        dispatch(Msg.Loading(false))
                        publish(Label.ToMain)
                    }
                    .onFailure {
                        dispatch(Msg.Loading(false))
                        publish(
                            Label.ShowError(
                                when(it){
                                    is ServerError -> AuthStore.Error.ServerError
                                    is NetworkError -> AuthStore.Error.NoNetworkConnection
                                    is InvalidCredentialsError -> AuthStore.Error.InvalidCredentialsError
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

    private object ReducerImpl: Reducer<State, Msg>{
        override fun State.reduce(msg: Msg): State =
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