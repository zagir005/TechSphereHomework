package com.zagirlek.add.model

import com.zagirlek.common.textfieldstate.AppTextFieldState
import com.zagirlek.common.validation.nickname.NicknameError
import com.zagirlek.common.validation.password.PasswordError
import com.zagirlek.common.validation.phone.PhoneError

data class AddUserModel(
    val nicknameTextField: AppTextFieldState<NicknameError> = AppTextFieldState(),
    val phoneTextField: AppTextFieldState<PhoneError> = AppTextFieldState(),
    val passwordTextField: AppTextFieldState<PasswordError> = AppTextFieldState(),
    val isAdmin: Boolean = false,
    val isCreateButtonEnabled: Boolean = false
)

