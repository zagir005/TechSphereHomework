package com.zagirlek.nytimes.ui.auth.model

import com.zagirlek.nytimes.ui.auth.elements.authdialog.AuthErrorInfo

sealed class AuthSideEffect{
    data class ShowErrorDialog(val authErrorInfo: AuthErrorInfo): AuthSideEffect()
}