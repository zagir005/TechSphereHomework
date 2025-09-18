package com.zagirlek.nytimes.ui.screen.login

import com.arkivanov.decompose.value.Value
import com.zagirlek.nytimes.ui.screen.login.cmp.state.LoginAction
import com.zagirlek.nytimes.ui.screen.login.cmp.state.LoginState

interface LoginComponent {
    val state: Value<LoginState>

    fun action(action: LoginAction)

}