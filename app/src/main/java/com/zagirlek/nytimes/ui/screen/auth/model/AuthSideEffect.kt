package com.zagirlek.nytimes.ui.screen.auth.model

import com.zagirlek.nytimes.ui.screen.auth.elements.authdialog.AuthErrorInfo

sealed class AuthSideEffect{
    data class ShowErrorDialog(val authErrorInfo: AuthErrorInfo): AuthSideEffect()
}