package com.zagirlek.auth.model

import com.zagirlek.auth.elements.authdialog.AuthErrorInfo

sealed class AuthSideEffect{
    data class ShowErrorDialog(val authErrorInfo: AuthErrorInfo): AuthSideEffect()
}