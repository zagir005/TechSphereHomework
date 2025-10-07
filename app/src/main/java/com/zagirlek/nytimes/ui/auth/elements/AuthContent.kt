package com.zagirlek.nytimes.ui.auth.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zagirlek.nytimes.R
import com.zagirlek.nytimes.ui.auth.elements.textfield.textfielderror.LoginTextFieldError
import com.zagirlek.nytimes.ui.auth.elements.textfield.textfielderror.PasswordTextFieldError
import com.zagirlek.nytimes.core.ui.elements.AppButton
import com.zagirlek.nytimes.core.ui.elements.AppTextField
import com.zagirlek.nytimes.ui.auth.model.AuthModel
import com.zagirlek.nytimes.ui.theme.Typography
import com.zagirlek.nytimes.ui.theme.robotoFlexFamily

@Composable
fun AuthContent(
    model: AuthModel,
    loginFieldValueChange: (String) -> Unit,
    passwordFieldValueChange: (String) -> Unit,
    onAuthButtonClick: () -> Unit,
    onContinueWithoutAuthClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(horizontal = 12.dp)
        ) {
            LoginHeader()

            Box(modifier = Modifier.weight(1f)){
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    LoginField(
                        value = model.loginField.value,
                        error = model.loginField.error,
                        onValueChange = loginFieldValueChange,
                        modifier = Modifier.fillMaxWidth()
                    )

                    PasswordField(
                        value = model.passwordField.value,
                        error = model.passwordField.error,
                        onValueChange = passwordFieldValueChange,
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (model.loading)
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.End)
                                .padding(end = 20.dp)
                        )
                    else
                        AppButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.End),
                            onClick = onAuthButtonClick,
                            enabled = model.isButtonEnabled
                        ) {
                            Text(text = stringResource(R.string.enter).uppercase())
                        }

                    TextButton(
                        onClick = onContinueWithoutAuthClick
                    ) {
                        Text(
                            text = stringResource(R.string.continue_without_auth),
                            fontFamily = robotoFlexFamily,
                            fontSize = Typography.bodyLarge.fontSize
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LoginField(
    value: String,
    error: LoginTextFieldError?,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    AppTextField(
        value = value,
        onValueChange = onValueChange,
        label = stringResource(R.string.login),
        errorMessage = when (error) {
            LoginTextFieldError.OnlyCyrillic -> stringResource(R.string.login_error_cyrillic)
            LoginTextFieldError.WrongLogin -> stringResource(R.string.login_error_invalid)
            null -> null
        },
        modifier = modifier
    )
}

@Composable
private fun PasswordField(
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
        errorMessage = when(error){
            PasswordTextFieldError.LengthLessThenSix -> stringResource(R.string.password_less_then_6)
            PasswordTextFieldError.LengthMoreThenTwelve -> stringResource(R.string.password_more_then_12)
            PasswordTextFieldError.WithoutLetter -> stringResource(R.string.password_without_letter)
            PasswordTextFieldError.WithoutNumber -> stringResource(R.string.password_without_number)
            null -> null
        },
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

@Composable
private fun LoginHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 24.sp,
            fontFamily = robotoFlexFamily
        )
        Image(
            painterResource(R.drawable.icon_main_loader),
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
    }
}