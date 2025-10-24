package com.zagirlek.addoredit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.zagirlek.addoredit.elements.AddOrEditUserScreenContent

@Composable
fun AddOrEditUserScreenUi(
    component: AddOrEditUserScreen
) {
    val model by component.model.collectAsState()

    AddOrEditUserScreenContent(
        addOrEditUserModel = model,
        onPhoneFieldEdit = { component.phoneEdit(it) },
        onNicknameFieldEdit = { component.nicknameEdit(it) },
        onPasswordFieldEdit = { component.passwordEdit(it) },
        onToggleIsAdmin = { component.toggleAdminStatus() },
        onCreateClick = { component.saveUser() },
        onCancelClick = { component.cancel() },
        modifier = Modifier.fillMaxWidth(),
    )
}