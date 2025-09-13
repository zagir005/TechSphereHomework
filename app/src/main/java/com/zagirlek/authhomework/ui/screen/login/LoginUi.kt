package com.zagirlek.authhomework.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.zagirlek.authhomework.R
import com.zagirlek.authhomework.ui.components.UnderlineTextField
import com.zagirlek.authhomework.ui.screen.login.cmp.state.LoginAction
import com.zagirlek.authhomework.ui.screen.login.cmp.state.textfield.textfielderror.LoginTextFieldError
import com.zagirlek.authhomework.ui.screen.login.cmp.state.textfield.textfielderror.PasswordTextFieldError
import com.zagirlek.authhomework.ui.screen.root.components.LoginComponent
import com.zagirlek.authhomework.ui.theme.robotoFlexFamily

@Composable
fun LoginUi(
    component: LoginComponent,
    modifier: Modifier = Modifier
) {
    val state by component.state.subscribeAsState()
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(horizontal = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = stringResource(R.string.app_name),
                color = Color.White,
                fontSize = 24.sp,
                fontFamily = robotoFlexFamily
            )
            Image(
                painterResource(R.drawable.spinner_main_icon),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
        }

        Box(modifier = Modifier.weight(1f)){
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                //login
                UnderlineTextField(
                    value = state.loginTextFieldState.value,
                    onValueChange = {
                        component.action(LoginAction.LoginTextChanged(it))
                    },
                    label = stringResource(R.string.login),
                    errorMessage = when(state.loginTextFieldState.error){
                        LoginTextFieldError.OnlyCyrillic -> stringResource(R.string.login_error_cyrillic)
                        LoginTextFieldError.WrongLogin -> stringResource(R.string.login_error_invalid)
                        null -> null
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                //password
                UnderlineTextField(
                    value = state.passwordTextFieldState.value,
                    onValueChange = {
                        component.action(LoginAction.PasswordTextChanged(it))
                    },
                    label = stringResource(R.string.password),
                    errorMessage = when(state.passwordTextFieldState.error){
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
                                    if (!passwordVisibility) R.drawable.outline_visibility_24
                                    else R.drawable.outline_visibility_off_24
                                ),
                                null
                            )
                        }
                    },
                    visualTransformation = if (passwordVisibility) PasswordVisualTransformation() else VisualTransformation.None,
                    modifier = Modifier.fillMaxWidth()
                )

                Row{
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = {
                            component.action(LoginAction.Submit)
                        },
                        shape = RoundedCornerShape(4.dp),
                        enabled = state.buttonEnabled
                    ) {
                        Text(text = stringResource(R.string.enter))
                    }
                }
            }
        }
    }
}

