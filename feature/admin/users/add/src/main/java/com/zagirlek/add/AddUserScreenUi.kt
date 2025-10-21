package com.zagirlek.add

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.zagirlek.add.elements.AddUserScreenContent

@Composable
fun AddUserScreenUi(
    component: AddUserScreen
) {
    val model by component.model.collectAsState()

    AddUserScreenContent(
        addUserModel = model,
        onPhoneFieldEdit = { component.phoneEdit(it) },
        onNicknameFieldEdit = { component.nicknameEdit(it) },
        onPasswordFieldEdit = { component.passwordEdit(it) },
        onToggleIsAdmin = { component.toggleAdminStatus() },
        onCreateClick = { component.saveUser() },
        modifier = Modifier.fillMaxWidth()
    )
}