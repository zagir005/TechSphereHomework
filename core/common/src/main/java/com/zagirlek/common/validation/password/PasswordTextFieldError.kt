package com.zagirlek.common.validation.password

import com.zagirlek.common.R
import com.zagirlek.common.textfieldstate.AppTextFieldError

sealed class PasswordError(open val msgRes: Int): AppTextFieldError {
    sealed class PasswordValidationError(override val msgRes: Int): PasswordError(msgRes){
        object LengthLessThenSix: PasswordValidationError(R.string.password_less_then_6)
        object LengthMoreThenTwelve: PasswordValidationError(R.string.password_more_then_12)
        object WithoutNumber: PasswordValidationError(R.string.password_without_number)
        object WithoutLetter: PasswordValidationError(R.string.password_without_letter)
        object NotEmpty: PasswordValidationError(R.string.password_cant_be_empty)
    }
}