package com.zagirlek.authhomework.ui.screen.login.cmp.state

import com.zagirlek.authhomework.ui.screen.login.cmp.state.textfield.TextFieldState
import com.zagirlek.authhomework.ui.screen.login.cmp.state.textfield.textfielderror.LoginTextFieldError
import com.zagirlek.authhomework.ui.screen.login.cmp.state.textfield.textfielderror.PasswordTextFieldError

data class LoginState (
    val passwordTextFieldState: TextFieldState<PasswordTextFieldError> = TextFieldState(),
    val loginTextFieldState: TextFieldState<LoginTextFieldError> = TextFieldState(),
    val buttonEnabled: Boolean = false
)