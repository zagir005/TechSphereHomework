package com.zagirlek.add.model

import com.zagirlek.add.store.AddUserStore
import com.zagirlek.common.model.UserStatus
import com.zagirlek.user.model.User

internal fun AddUserStore.State.toUser(): User = User(
    phone = phoneTextField.value,
    nickname = nicknameTextField.value,
    password = passwordTextField.value,
    status = if (isAdmin) UserStatus.ADMIN else UserStatus.CLIENT
)

internal fun AddUserStore.State.toModel(): AddUserModel = AddUserModel(
    nicknameTextField = nicknameTextField,
    phoneTextField = phoneTextField,
    passwordTextField = passwordTextField,
    isAdmin = isAdmin,
    isCreateButtonEnabled = isCreateButtonEnabled
)