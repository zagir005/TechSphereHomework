package com.zagirlek.auth

import com.zagirlek.auth.model.AuthModel
import com.zagirlek.auth.model.AuthSideEffect
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface AuthScreen {
    val model: StateFlow<AuthModel>
    val sideEffect: SharedFlow<AuthSideEffect>

    fun loginFieldValueChanged(value: String)
    fun passwordFieldValueChanged(value: String)
    fun onAuth()
    fun continueWithoutAuth()
}