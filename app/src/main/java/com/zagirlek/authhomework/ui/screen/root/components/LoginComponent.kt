package com.zagirlek.authhomework.ui.screen.root.components

import com.arkivanov.decompose.value.Value
import com.zagirlek.authhomework.ui.screen.login.cmp.state.LoginAction
import com.zagirlek.authhomework.ui.screen.login.cmp.state.LoginState

interface LoginComponent {
    val state: Value<LoginState>

    fun action(loginAction: LoginAction)
}