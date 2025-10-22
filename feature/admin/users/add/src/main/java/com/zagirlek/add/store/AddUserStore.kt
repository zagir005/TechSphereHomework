package com.zagirlek.add.store

import com.arkivanov.mvikotlin.core.store.Store
import com.zagirlek.add.store.AddUserStore.Intent
import com.zagirlek.add.store.AddUserStore.Label
import com.zagirlek.add.store.AddUserStore.State
import com.zagirlek.common.textfieldstate.AppTextFieldState
import com.zagirlek.common.validation.nickname.NicknameError
import com.zagirlek.common.validation.password.PasswordError
import com.zagirlek.common.validation.phone.PhoneError

interface AddUserStore: Store<Intent, State, Label> {
    data class State(
        val nicknameTextField: AppTextFieldState<NicknameError> = AppTextFieldState(),
        val phoneTextField: AppTextFieldState<PhoneError> = AppTextFieldState(),
        val passwordTextField: AppTextFieldState<PasswordError> = AppTextFieldState(),
        val isAdmin: Boolean = false,
        val isCreateButtonEnabled: Boolean = false
    )
    sealed class Intent{
        data class NicknameEdit(val value: String): Intent()
        data class PasswordEdit(val value: String): Intent()
        data class PhoneEdit(val value: String): Intent()
        data object ToggleIsAdminStatus: Intent()
        data object Save: Intent()
    }
    sealed class Label{
        data object OnSave: Label()
    }
}