package com.zagirlek.common.validation.phone

fun validatePhone(phone: String): PhoneTextFieldError? {
    return when {
        phone.isEmpty() -> null
        phone.any { it.isLetter() } -> PhoneTextFieldError.OnlyNumbers
        else -> null
    }
}