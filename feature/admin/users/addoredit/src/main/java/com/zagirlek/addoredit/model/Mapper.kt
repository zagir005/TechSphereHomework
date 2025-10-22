package com.zagirlek.addoredit.model

import com.zagirlek.addoredit.store.AddOrEditUserStore
import com.zagirlek.common.model.UserStatus
import com.zagirlek.user.model.User

internal fun AddOrEditUserStore.State.toUser(): User = User(
    phone = phoneTextField.value,
    nickname = nicknameTextField.value,
    password = passwordTextField.value,
    status = if (isAdmin) UserStatus.ADMIN else UserStatus.CLIENT
)

internal fun AddOrEditUserStore.State.toModel(): AddOrEditUserModel = AddOrEditUserModel(
    nicknameTextField = nicknameTextField,
    phoneTextField = phoneTextField,
    passwordTextField = passwordTextField,
    isAdmin = isAdmin,
    isAcceptButtonEnabled = isCreateButtonEnabled
)

