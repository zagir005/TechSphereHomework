package com.zagirlek.common.validation.password

fun validatePassword(password: String): PasswordError.PasswordValidationError? {
    return when {
        password.isEmpty() -> PasswordError.PasswordValidationError.NotEmpty
        password.length < 6 -> PasswordError.PasswordValidationError.LengthLessThenSix
        password.length > 12 -> PasswordError.PasswordValidationError.LengthMoreThenTwelve
        password.toCharArray().none { it.isDigit() } -> PasswordError.PasswordValidationError.WithoutNumber
        password.toCharArray().none { it.isLetter() } -> PasswordError.PasswordValidationError.WithoutLetter
        else -> null
    }
}