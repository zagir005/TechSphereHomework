package com.zagirlek.authhomework.ui.screen.login.cmp.state.textfield.textfielderror

sealed class LoginTextFieldError: TextFieldError {
    object OnlyCyrillic: LoginTextFieldError()

    object WrongLogin: LoginTextFieldError()
}