package com.zagirlek.nytimes.ui.screen.auth.elements.textfield.textfielderror

sealed class PasswordTextFieldError: TextFieldError {

    object LengthLessThenSix: PasswordTextFieldError()

    object LengthMoreThenTwelve: PasswordTextFieldError()

    object WithoutNumber: PasswordTextFieldError()

    object WithoutLetter: PasswordTextFieldError()
}