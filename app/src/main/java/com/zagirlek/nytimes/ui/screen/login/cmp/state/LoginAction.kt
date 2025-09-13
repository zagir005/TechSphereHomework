package com.zagirlek.nytimes.ui.screen.login.cmp.state

sealed class LoginAction{
    data class LoginTextChanged(val text: String): LoginAction()

    data class PasswordTextChanged(val text: String): LoginAction()

    data object Submit: LoginAction()

}