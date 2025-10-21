package com.zagirlek.ui.elements.textfield

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.zagirlek.common.validation.nickname.NicknameTextFieldError
import com.zagirlek.ui.R

@Composable
fun NicknameField(
    value: String,
    error: NicknameTextFieldError?,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    AppTextField(
        value = value,
        onValueChange = onValueChange,
        label = stringResource(R.string.login),
        errorMessage = error?.let { stringResource(error.msgRes) },
        modifier = modifier
    )
}