package com.zagirlek.nytimes.ui.screen.auth.model

import com.zagirlek.nytimes.ui.screen.auth.elements.textfield.TextFieldState
import com.zagirlek.nytimes.ui.screen.auth.elements.textfield.textfielderror.LoginTextFieldError
import com.zagirlek.nytimes.ui.screen.auth.elements.textfield.textfielderror.PasswordTextFieldError

data class AuthModel(
    val loginField: TextFieldState<LoginTextFieldError> = TextFieldState(),
    val passwordField: TextFieldState<PasswordTextFieldError> = TextFieldState(),
    val loading: Boolean = false,
    val isButtonEnabled: Boolean = false
)
