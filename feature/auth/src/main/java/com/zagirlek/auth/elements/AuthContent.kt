package com.zagirlek.auth.elements

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zagirlek.auth.model.AuthModel
import com.zagirlek.ui.R
import com.zagirlek.ui.elements.AppButton
import com.zagirlek.ui.elements.textfield.NicknameField
import com.zagirlek.ui.elements.textfield.PasswordField
import com.zagirlek.ui.theme.Typography
import com.zagirlek.ui.theme.robotoFlexFamily

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
                    NicknameField(
                        state = model.nicknameField,
                        onValueChange = loginFieldValueChange,
                        modifier = Modifier.fillMaxWidth()
                    )

                    PasswordField(
                        state = model.passwordField,
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