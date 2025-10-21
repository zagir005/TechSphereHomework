package com.zagirlek.add.model

import com.zagirlek.common.textfieldstate.AppTextFieldState
import com.zagirlek.common.validation.nickname.NicknameTextFieldError
import com.zagirlek.common.validation.password.PasswordTextFieldError
import com.zagirlek.common.validation.phone.PhoneTextFieldError

data class AddUserModel(
    val nicknameTextField: AppTextFieldState<NicknameTextFieldError> = AppTextFieldState(),
    val phoneTextField: AppTextFieldState<PhoneTextFieldError> = AppTextFieldState(),
    val passwordTextField: AppTextFieldState<PasswordTextFieldError> = AppTextFieldState(),
    val isAdmin: Boolean = false,
    val isContinueButtonEnabled: Boolean = false
)

