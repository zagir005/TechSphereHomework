package com.zagirlek.ui.elements.textfield

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.zagirlek.common.textfieldstate.AppTextFieldState
import com.zagirlek.common.validation.nickname.NicknameTextFieldError
import com.zagirlek.ui.R

@Composable
fun NicknameField(
    state: AppTextFieldState<NicknameTextFieldError>,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    AppTextField(
        value = state.value,
        onValueChange = onValueChange,
        label = stringResource(R.string.login),
        errorMessage = state.error?.msgRes?.let { stringResource(it) },
        modifier = modifier
    )
}