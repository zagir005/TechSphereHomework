package com.zagirlek.nytimes.ui.auth.elements.textfield.textfielderror

sealed class LoginTextFieldError: TextFieldError {
    object OnlyCyrillic: LoginTextFieldError()

    object WrongLogin: LoginTextFieldError()
}