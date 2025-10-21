package com.zagirlek.ui.elements.textfield

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.zagirlek.common.validation.password.PasswordTextFieldError
import com.zagirlek.ui.R

@Composable
fun PasswordField(
    value: String,
    error: PasswordTextFieldError?,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    var passwordVisibility by rememberSaveable { mutableStateOf(true) }

    AppTextField(
        value = value,
        onValueChange = onValueChange,
        label = stringResource(R.string.password),
        errorMessage = error?.let { stringResource(error.msgRes) },
        trailingIcon = {
            IconButton(
                onClick = {
                    passwordVisibility = !passwordVisibility
                }
            ) {
                Icon(
                    painterResource(
                        if (!passwordVisibility) R.drawable.ic_visibility
                        else R.drawable.ic_visibility_off
                    ),
                    null
                )
            }
        },
        visualTransformation = if (passwordVisibility) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = modifier
    )
}