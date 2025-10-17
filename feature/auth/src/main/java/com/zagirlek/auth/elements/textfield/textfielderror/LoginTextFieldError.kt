package com.zagirlek.auth.elements.textfield.textfielderror

sealed class LoginTextFieldError: TextFieldError {
    object OnlyCyrillic: LoginTextFieldError()

    object WrongLogin: LoginTextFieldError()
}