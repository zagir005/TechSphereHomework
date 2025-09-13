package com.zagirlek.nytimes.ui.screen.login.cmp.state

import com.zagirlek.nytimes.ui.screen.login.cmp.state.textfield.TextFieldState
import com.zagirlek.nytimes.ui.screen.login.cmp.state.textfield.textfielderror.LoginTextFieldError
import com.zagirlek.nytimes.ui.screen.login.cmp.state.textfield.textfielderror.PasswordTextFieldError

data class LoginState (
    val loginTextFieldState: TextFieldState<LoginTextFieldError> = TextFieldState(),
    val passwordTextFieldState: TextFieldState<PasswordTextFieldError> = TextFieldState(),
    val buttonEnabled: Boolean = false
)