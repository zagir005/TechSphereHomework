package com.zagirlek.common.validation.phone

import com.zagirlek.common.R
import com.zagirlek.common.textfieldstate.AppTextFieldError

sealed class PhoneTextFieldError(val msgRes: Int): AppTextFieldError {
    object OnlyNumbers: PhoneTextFieldError(R.string.phone_should_contain_only_numbers)
}