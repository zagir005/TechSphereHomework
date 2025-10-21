package com.zagirlek.common.validation.nickname

fun validateNickname(login: String): NicknameTextFieldError? {
    return when {
        login.isEmpty() -> null
        !login.matches(Regex("^[^A-Za-z]+$")) -> NicknameTextFieldError.OnlyCyrillic
        else -> null
    }
}