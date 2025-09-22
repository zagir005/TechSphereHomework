package com.zagirlek.nytimes.ui.screen.login.cmp.state

import com.zagirlek.nytimes.core.base.component.Action

sealed class LoginAction: Action{
    data class LoginTextChanged(val text: String): LoginAction()

    data class PasswordTextChanged(val text: String): LoginAction()

    data object Submit: LoginAction()

    data object ContinueWithoutAuth: LoginAction()
}