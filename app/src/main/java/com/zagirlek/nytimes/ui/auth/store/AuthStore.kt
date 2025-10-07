package com.zagirlek.nytimes.ui.auth.store


import com.arkivanov.mvikotlin.core.store.Store
import com.zagirlek.nytimes.ui.auth.elements.textfield.TextFieldState
import com.zagirlek.nytimes.ui.auth.elements.textfield.textfielderror.LoginTextFieldError
import com.zagirlek.nytimes.ui.auth.elements.textfield.textfielderror.PasswordTextFieldError


interface AuthStore: Store<AuthStore.Intent, AuthStore.State, AuthStore.Label>{

    data class State (
        val loginTextFieldState: TextFieldState<LoginTextFieldError> = TextFieldState(),
        val passwordTextFieldState: TextFieldState<PasswordTextFieldError> = TextFieldState(),
        val isAuthAvailable: Boolean = false,
        val loading: Boolean = false
    )

    sealed class Intent {
        data class LoginTextFieldChange(val text: String): Intent()
        data class PasswordTextFieldChange(val text: String): Intent()
        data object AuthWithoutLogin: Intent()
        data object Auth: Intent()
    }


    sealed class Label {
        data class ShowError(val error: Error): Label()
        data object ToMain: Label()
    }

    sealed class Error {
        data object NoNetworkConnection: Error()
        data object ServerError: Error()
        data object InvalidCredentials: Error()
        data object UnknownError: Error()
    }
}