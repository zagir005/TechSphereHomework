package com.zagirlek.add.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zagirlek.add.model.AddUserModel
import com.zagirlek.common.textfieldstate.AppTextFieldState
import com.zagirlek.common.validation.phone.PhoneTextFieldError
import com.zagirlek.ui.R
import com.zagirlek.ui.elements.textfield.AppTextField
import com.zagirlek.ui.elements.textfield.NicknameField
import com.zagirlek.ui.elements.textfield.PasswordField

@Composable
internal fun AddUserScreenContent(
    addUserModel: AddUserModel,
    onPhoneFieldEdit: (String) -> Unit,
    onNicknameFieldEdit: (String) -> Unit,
    onPasswordFieldEdit: (String) -> Unit,
    onToggleIsAdmin: () -> Unit,
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    with(addUserModel){
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = modifier
        ) {
            NicknameField(
                state = nicknameTextField,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = onNicknameFieldEdit
            )
            PhoneField(
                state = phoneTextField,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = onPhoneFieldEdit
            )
            PasswordField(
                state = passwordTextField,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = onPasswordFieldEdit
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(com.zagirlek.add.R.string.admin)
                )
                Spacer(
                    modifier = Modifier.width(4.dp)
                )
                Checkbox(
                    checked = isAdmin,
                    onCheckedChange = { onToggleIsAdmin() }
                )
            }
            Button(
                onClick = onCreateClick,
                enabled = isCreateButtonEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End)
            ) {
                Text(text = stringResource(R.string.save))
            }
        }
    }
}

@Composable
fun PhoneField(
    state: AppTextFieldState<PhoneTextFieldError>,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    AppTextField(
        value = state.value,
        onValueChange = onValueChange,
        label = stringResource(R.string.phone),
        errorMessage = state.error?.msgRes?.let { stringResource(it) },
        modifier = modifier
    )
}

