package com.zagirlek.add.store

import com.arkivanov.mvikotlin.core.store.Store
import com.zagirlek.add.store.AddUserStore.Intent
import com.zagirlek.add.store.AddUserStore.State
import com.zagirlek.common.textfieldstate.AppTextFieldState
import com.zagirlek.common.validation.nickname.NicknameTextFieldError
import com.zagirlek.common.validation.password.PasswordTextFieldError
import com.zagirlek.common.validation.phone.PhoneTextFieldError

interface AddUserStore: Store<Intent, State, Nothing> {
    data class State(
        val nicknameTextField: AppTextFieldState<NicknameTextFieldError> = AppTextFieldState(),
        val phoneTextField: AppTextFieldState<PhoneTextFieldError> = AppTextFieldState(),
        val passwordTextField: AppTextFieldState<PasswordTextFieldError> = AppTextFieldState(),
        val isAdmin: Boolean = false,
        val isContinueButtonEnabled: Boolean = true
    )
    sealed class Intent{
        data class NicknameEdit(val value: String): Intent()
        data class PasswordEdit(val value: String): Intent()
        data class PhoneEdit(val value: String): Intent()
        data object ToggleIsAdminStatus: Intent()
        data object Save: Intent()
    }
}