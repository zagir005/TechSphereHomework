package com.zagirlek.common.validation.nickname

fun validateNickname(login: String): NicknameError.NicknameValidationError? {
    return when {
        login.isEmpty() -> NicknameError.NicknameValidationError.NotEmpty
        !login.matches(Regex("^[^A-Za-z]+$")) -> NicknameError.NicknameValidationError.OnlyCyrillic
        else -> null
    }
}