package com.zagirlek.auth.elements.authdialog

import com.zagirlek.ui.R

sealed class AuthErrorInfo(
    val title: Int = R.string.error,
    val description: Int
) {
    data object NoNetworkConnection: AuthErrorInfo(
        description = R.string.check_network_connection
    )
    data object ServerErrorInfo: AuthErrorInfo(
        description = R.string.server_error_try_later
    )
    data object InvalidCredentialsErrorInfo: AuthErrorInfo(
        description = R.string.invalid_credentials_check_please
    )
    data object UnknownErrorInfo: AuthErrorInfo(
        description = R.string.weird_error_try_later
    )
}