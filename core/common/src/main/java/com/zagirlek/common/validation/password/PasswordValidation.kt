package com.zagirlek.common.validation.password

fun validatePassword(password: String): PasswordTextFieldError? {
    return when {
        password.isEmpty() -> null
        password.length < 6 -> PasswordTextFieldError.LengthLessThenSix
        password.length > 12 -> PasswordTextFieldError.LengthMoreThenTwelve
        password.toCharArray().none { it.isDigit() } -> PasswordTextFieldError.WithoutNumber
        password.toCharArray().none { it.isLetter() } -> PasswordTextFieldError.WithoutLetter
        else -> null
    }
}