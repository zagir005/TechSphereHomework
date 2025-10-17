package com.zagirlek.auth.model

import com.zagirlek.auth.elements.authdialog.AuthErrorInfo
import com.zagirlek.auth.store.AuthStore

internal fun AuthStore.State.toModel(): AuthModel =
    AuthModel(
        loginField = loginTextFieldState,
        passwordField = passwordTextFieldState,
        loading = loading,
        isButtonEnabled = isAuthAvailable
    )

internal fun AuthStore.Label.ShowError.toModel(): AuthErrorInfo =
    when(this.error){
        AuthStore.Error.InvalidCredentials -> AuthErrorInfo.InvalidCredentialsErrorInfo
        AuthStore.Error.NoNetworkConnection -> AuthErrorInfo.NoNetworkConnection
        AuthStore.Error.ServerError -> AuthErrorInfo.ServerErrorInfo
        AuthStore.Error.UnknownError -> AuthErrorInfo.UnknownErrorInfo
    }
