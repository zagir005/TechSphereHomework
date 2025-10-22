package com.zagirlek.common.validation.phone

fun validatePhone(phone: String): PhoneError.PhoneValidationError? {
    return when {
        phone.isEmpty() -> PhoneError.PhoneValidationError.NotEmpty
        phone.any { it.isLetter() } -> PhoneError.PhoneValidationError.OnlyNumbers
        else -> null
    }
}