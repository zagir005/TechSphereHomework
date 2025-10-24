package com.zagirlek.auth.model

import com.zagirlek.common.textfieldstate.AppTextFieldState
import com.zagirlek.common.validation.nickname.NicknameError
import com.zagirlek.common.validation.password.PasswordError

data class
AuthModel(
    val nicknameField: AppTextFieldState<NicknameError.NicknameValidationError> = AppTextFieldState(),
    val passwordField: AppTextFieldState<PasswordError.PasswordValidationError> = AppTextFieldState(),
    val loading: Boolean = false,
    val isButtonEnabled: Boolean = false
)
