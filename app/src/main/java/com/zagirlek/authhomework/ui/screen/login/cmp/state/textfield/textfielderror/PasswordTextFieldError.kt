package com.zagirlek.authhomework.ui.screen.login.cmp.state.textfield.textfielderror

sealed class PasswordTextFieldError: TextFieldError {

    object LengthLessThenSix: PasswordTextFieldError()

    object LengthMoreThenTwelve: PasswordTextFieldError()

    object WithoutNumber: PasswordTextFieldError()

    object WithoutLetter: PasswordTextFieldError()
}