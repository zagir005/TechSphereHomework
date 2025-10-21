package com.zagirlek.auth.store


import com.arkivanov.mvikotlin.core.store.Store
import com.zagirlek.common.textfieldstate.AppTextFieldState
import com.zagirlek.common.validation.nickname.NicknameTextFieldError
import com.zagirlek.common.validation.password.PasswordTextFieldError


internal interface AuthStore: Store<AuthStore.Intent, AuthStore.State, AuthStore.Label>{
    data class State (
        val loginTextFieldState: AppTextFieldState<NicknameTextFieldError> = AppTextFieldState(),
        val passwordTextFieldState: AppTextFieldState<PasswordTextFieldError> = AppTextFieldState(),
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
        data object ToClient: Label()
        data object ToAdmin: Label()
    }
    sealed class Error {
        data object NoNetworkConnection: Error()
        data object ServerError: Error()
        data object InvalidCredentials: Error()
        data object UnknownError: Error()
    }
}