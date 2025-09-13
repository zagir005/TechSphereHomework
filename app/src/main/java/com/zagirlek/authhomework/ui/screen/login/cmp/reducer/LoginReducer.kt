package com.zagirlek.authhomework.ui.screen.login.cmp.reducer

import android.util.Log
import com.zagirlek.authhomework.ui.screen.login.cmp.state.LoginAction
import com.zagirlek.authhomework.ui.screen.login.cmp.state.LoginState
import com.zagirlek.authhomework.ui.screen.login.cmp.state.textfield.TextFieldState
import com.zagirlek.authhomework.ui.screen.login.cmp.state.textfield.textfielderror.LoginTextFieldError
import com.zagirlek.authhomework.ui.screen.login.cmp.state.textfield.textfielderror.PasswordTextFieldError

class LoginReducer() {
    fun reduce(state: LoginState, action: LoginAction): LoginState {
        return when(action){
            is LoginAction.LoginTextChanged -> {
                val loginError: LoginTextFieldError? = validateLogin(action.text)
                state.copy(
                    loginTextFieldState = TextFieldState(
                        value = action.text,
                        error = loginError
                    ),
                    buttonEnabled = loginError == null && !state.passwordTextFieldState.hasError()
                            && (action.text.isNotEmpty() && state.passwordTextFieldState.value.isNotEmpty())
                )
            }
            is LoginAction.PasswordTextChanged -> {
                val passwordError: PasswordTextFieldError? = validatePassword(action.text)
                state.copy(
                    passwordTextFieldState = TextFieldState(
                        value = action.text,
                        error = validatePassword(action.text)
                    ),
                    buttonEnabled = passwordError == null && !state.loginTextFieldState.hasError()
                            && (action.text.isNotEmpty() && state.loginTextFieldState.value.isNotEmpty())
                )
            }
            LoginAction.Submit -> {
                return state
            }
        }
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