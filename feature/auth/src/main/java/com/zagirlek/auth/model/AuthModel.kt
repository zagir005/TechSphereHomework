package com.zagirlek.auth.model

import com.zagirlek.common.textfieldstate.AppTextFieldState
import com.zagirlek.common.validation.nickname.NicknameTextFieldError
import com.zagirlek.common.validation.password.PasswordTextFieldError

data class
AuthModel(
    val nicknameField: AppTextFieldState<NicknameTextFieldError> = AppTextFieldState(),
    val passwordField: AppTextFieldState<PasswordTextFieldError> = AppTextFieldState(),
    val loading: Boolean = false,
    val isButtonEnabled: Boolean = false
)
