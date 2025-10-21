package com.zagirlek.common.validation.password

import com.zagirlek.common.R
import com.zagirlek.common.textfieldstate.AppTextFieldError

sealed class PasswordTextFieldError(val msgRes: Int): AppTextFieldError {
    object LengthLessThenSix: PasswordTextFieldError(R.string.password_less_then_6)
    object LengthMoreThenTwelve: PasswordTextFieldError(R.string.password_more_then_12)
    object WithoutNumber: PasswordTextFieldError(R.string.password_without_letter)
    object WithoutLetter: PasswordTextFieldError(R.string.password_without_number)
}