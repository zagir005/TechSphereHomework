package com.zagirlek.nytimes.ui.auth

import com.zagirlek.nytimes.ui.auth.model.AuthModel
import com.zagirlek.nytimes.ui.auth.model.AuthSideEffect
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