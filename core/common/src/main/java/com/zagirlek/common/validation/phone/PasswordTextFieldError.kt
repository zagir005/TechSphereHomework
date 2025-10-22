package com.zagirlek.common.validation.phone

import com.zagirlek.common.R
import com.zagirlek.common.textfieldstate.AppTextFieldError

sealed class PhoneError(open val msgRes: Int): AppTextFieldError {
    sealed class PhoneValidationError(override val msgRes: Int): PhoneError(msgRes){
        object OnlyNumbers: PhoneValidationError(R.string.phone_should_contain_only_numbers)
        object NotEmpty: PhoneValidationError(R.string.phone_cant_be_empty)
    }
    sealed class PhoneTextFieldError(override val msgRes: Int): PhoneError(msgRes){
        object Duplicate: PhoneTextFieldError(R.string.phone_duplicate_alert)
    }
}