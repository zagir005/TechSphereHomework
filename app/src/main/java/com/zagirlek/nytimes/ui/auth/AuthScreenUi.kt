package com.zagirlek.nytimes.ui.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.zagirlek.nytimes.ui.auth.elements.AuthContent
import com.zagirlek.nytimes.ui.auth.elements.authdialog.AuthErrorDialog
import com.zagirlek.nytimes.ui.auth.elements.authdialog.AuthErrorDialogState
import com.zagirlek.nytimes.ui.auth.model.AuthSideEffect

@Composable
fun AuthScreenUi(
    component: AuthScreen,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val state by component.model.collectAsState()
    val sideEffect = component.sideEffect

    var authErrorDialogState by remember {
        mutableStateOf<AuthErrorDialogState?>(null)
    }

    LaunchedEffect(sideEffect) {
        sideEffect.collect {
            when(it){
                is AuthSideEffect.ShowErrorDialog -> authErrorDialogState = AuthErrorDialogState(
                    title = context.getString(it.authErrorInfo.title),
                    description = context.getString(it.authErrorInfo.description)
                )
            }
        }
    }

    authErrorDialogState?.let {
        AuthErrorDialog(
            state = it,
            onDismiss = { authErrorDialogState = null }
        )
    }

    AuthContent(
        model = state,
        loginFieldValueChange = { component.loginFieldValueChanged(it) },
        passwordFieldValueChange = { component.passwordFieldValueChanged(it) },
        onAuthButtonClick = { component.onAuth() },
        onContinueWithoutAuthClick = { component.continueWithoutAuth() },
        modifier = modifier
    )
}