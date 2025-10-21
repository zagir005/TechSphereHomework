package com.zagirlek.common.validation.nickname

import com.zagirlek.common.R
import com.zagirlek.common.textfieldstate.AppTextFieldError

sealed class NicknameTextFieldError(val msgRes: Int): AppTextFieldError {
    object OnlyCyrillic: NicknameTextFieldError(msgRes = R.string.login_error_cyrillic)
}