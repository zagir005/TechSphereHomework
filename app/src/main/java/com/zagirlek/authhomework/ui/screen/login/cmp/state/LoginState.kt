package com.zagirlek.authhomework.ui.screen.login.cmp.state

data class LoginState (
    val passwordTextFieldState: TextFieldState = TextFieldState(),
    val loginTextFieldState: TextFieldState = TextFieldState(),
    val buttonEnabled: Boolean = false
)