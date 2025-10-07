package com.zagirlek.nytimes.ui.auth.model

import com.zagirlek.nytimes.ui.auth.elements.authdialog.AuthErrorInfo
import com.zagirlek.nytimes.ui.auth.store.AuthStore

fun AuthStore.State.toModel(): AuthModel =
    AuthModel(
        loginField = loginTextFieldState,
        passwordField = passwordTextFieldState,
        loading = loading,
        isButtonEnabled = isAuthAvailable
    )

fun AuthStore.Label.ShowError.toModel(): AuthErrorInfo =
    when(this.error){
        AuthStore.Error.InvalidCredentials -> AuthErrorInfo.InvalidCredentialsErrorInfo
        AuthStore.Error.NoNetworkConnection -> AuthErrorInfo.NoNetworkConnection
        AuthStore.Error.ServerError -> AuthErrorInfo.ServerErrorInfo
        AuthStore.Error.UnknownError -> AuthErrorInfo.UnknownErrorInfo
    }
