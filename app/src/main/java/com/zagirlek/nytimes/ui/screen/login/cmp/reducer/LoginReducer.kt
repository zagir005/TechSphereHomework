package com.zagirlek.nytimes.ui.screen.login.cmp.reducer

import com.zagirlek.nytimes.core.base.reducer.Reducer
import com.zagirlek.nytimes.ui.screen.login.cmp.state.LoginState
import com.zagirlek.nytimes.ui.screen.login.cmp.state.textfield.TextFieldState
import com.zagirlek.nytimes.ui.screen.login.cmp.state.textfield.textfielderror.LoginTextFieldError
import com.zagirlek.nytimes.ui.screen.login.cmp.state.textfield.textfielderror.PasswordTextFieldError

class LoginReducer(): Reducer<LoginState, LoginMutation> {
    override fun reduce(state: LoginState, mutation: LoginMutation): LoginState {
        return when(mutation){
            is LoginMutation.LoginTextChanged -> {
                val loginTextFieldState = TextFieldState(
                    value = mutation.text,
                    error = validateLogin(mutation.text)
                )
                state.copy(
                    loginTextFieldState = loginTextFieldState,
                    buttonEnabled = isButtonEnabled(
                        loginTextFieldState = loginTextFieldState,
                        passwordTextFieldState = state.passwordTextFieldState
                    )
                )
            }
            is LoginMutation.PasswordTextChanged -> {
                val passwordTextFieldState: TextFieldState<PasswordTextFieldError> = TextFieldState(
                    value = mutation.text,
                    error = validatePassword(mutation.text)
                )
                state.copy(
                    passwordTextFieldState = passwordTextFieldState,
                    buttonEnabled = isButtonEnabled(
                        loginTextFieldState = state.loginTextFieldState,
                        passwordTextFieldState = passwordTextFieldState
                    )
                )
            }
        }
    }

    private fun isButtonEnabled(
        loginTextFieldState: TextFieldState<LoginTextFieldError>,
        passwordTextFieldState: TextFieldState<PasswordTextFieldError>,
    ): Boolean {
        return loginTextFieldState.isNotEmpty() && passwordTextFieldState.isNotEmpty()
                && loginTextFieldState.isValid() && passwordTextFieldState.isValid()
    }

    private fun validateLogin(login: String): LoginTextFieldError? {
        return when {
            login.isEmpty() -> null
            !login.matches(Regex("^[^A-Za-z]+$")) -> LoginTextFieldError.OnlyCyrillic
            login != "Логин_Юзера" -> LoginTextFieldError.WrongLogin
            else -> null
        }
    }

    private fun validatePassword(password: String): PasswordTextFieldError? {
        return when {
            password.isEmpty() -> null
            password.length < 6 -> PasswordTextFieldError.LengthLessThenSix
            password.length > 12 -> PasswordTextFieldError.LengthMoreThenTwelve
            password.toCharArray().none { it.isDigit() } -> PasswordTextFieldError.WithoutNumber
            password.toCharArray().none { it.isLetter() } -> PasswordTextFieldError.WithoutLetter
            else -> null
        }
    }

}