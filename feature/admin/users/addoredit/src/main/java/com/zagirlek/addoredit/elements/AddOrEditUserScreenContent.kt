package com.zagirlek.addoredit.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zagirlek.addoredit.R
import com.zagirlek.addoredit.model.AddOrEditUserModel
import com.zagirlek.common.textfieldstate.AppTextFieldState
import com.zagirlek.common.validation.phone.PhoneError
import com.zagirlek.ui.elements.textfield.AppTextField
import com.zagirlek.ui.elements.textfield.NicknameField
import com.zagirlek.ui.elements.textfield.PasswordField

@Composable
internal fun AddOrEditUserScreenContent(
    addOrEditUserModel: AddOrEditUserModel,
    onPhoneFieldEdit: (String) -> Unit,
    onNicknameFieldEdit: (String) -> Unit,
    onPasswordFieldEdit: (String) -> Unit,
    onToggleIsAdmin: () -> Unit,
    onCreateClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    with(addOrEditUserModel){
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = modifier
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            Text(
                text = stringResource(R.string.user),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
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
                    text = stringResource(R.string.admin)
                )
                Spacer(
                    modifier = Modifier.width(4.dp)
                )
                Checkbox(
                    checked = isAdmin,
                    onCheckedChange = { onToggleIsAdmin() }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = onCancelClick,
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
                Button(
                    onClick = onCreateClick,
                    enabled = isAcceptButtonEnabled,
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                ) {
                    Text(text = stringResource(com.zagirlek.ui.R.string.save))
                }
            }

        }
    }
}

@Composable
fun PhoneField(
    state: AppTextFieldState<PhoneError>,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    AppTextField(
        value = state.value,
        onValueChange = onValueChange,
        label = stringResource(com.zagirlek.ui.R.string.phone),
        errorMessage = state.error?.msgRes?.let { stringResource(it) },
        modifier = modifier
    )
}


