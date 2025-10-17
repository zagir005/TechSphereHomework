package com.zagirlek.auth.model

import com.zagirlek.auth.elements.textfield.TextFieldState
import com.zagirlek.auth.elements.textfield.textfielderror.LoginTextFieldError
import com.zagirlek.auth.elements.textfield.textfielderror.PasswordTextFieldError

data class AuthModel(
    val loginField: TextFieldState<LoginTextFieldError> = TextFieldState(),
    val passwordField: TextFieldState<PasswordTextFieldError> = TextFieldState(),
    val loading: Boolean = false,
    val isButtonEnabled: Boolean = false
)
