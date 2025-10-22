package com.zagirlek.common.validation.nickname

import com.zagirlek.common.R
import com.zagirlek.common.textfieldstate.AppTextFieldError

sealed class NicknameError(open val msgRes: Int): AppTextFieldError {
    sealed class NicknameValidationError(override val msgRes: Int): NicknameError(msgRes) {
        object OnlyCyrillic: NicknameValidationError(R.string.nickname_error_cyrillic)
        object NotEmpty: NicknameValidationError(R.string.nickname_cant_be_empty)
    }
    sealed class NicknameTextFieldError(override val msgRes: Int): NicknameError(msgRes){
        object Duplicate: NicknameTextFieldError(R.string.nickname_duplicate_alert)
    }
}