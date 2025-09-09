package com.zagirlek.authhomework.ui.screen.login.cmp.reducer

import com.zagirlek.authhomework.ui.screen.login.cmp.state.LoginAction
import com.zagirlek.authhomework.ui.screen.login.cmp.state.LoginState
import com.zagirlek.authhomework.ui.screen.login.cmp.state.TextFieldState

class LoginReducer() {

    fun reduce(state: LoginState, action: LoginAction): LoginState {
        return when(action){
            is LoginAction.LoginTextChanged -> state.copy(
                    loginTextFieldState = TextFieldState(
                        value = action.text,
                        error = validateLogin(action.text)
                    ),
                    buttonEnabled = state.loginTextFieldState.hasError() || state.passwordTextFieldState.hasError()
                )
            is LoginAction.PasswordTextChanged -> state.copy(
                    passwordTextFieldState = TextFieldState(
                        value = action.text,
                        error = validatePassword(action.text)
                    ),
                    buttonEnabled = !(state.passwordTextFieldState.hasError() || state.passwordTextFieldState.hasError())
                )
            LoginAction.Submit -> {
                //по идее тут мы залогиниться
                return state
            }
        }
    }

    private fun validateLogin(login: String): String? {
        return if (!login.matches(Regex("^[^A-Za-z]+$")))
            "Логин пользователя должен быть на кириллице"
        else if (login != "Логин_Юзера")
            "Неверный логин"
        else
            null
    }

    private fun validatePassword(password: String): String? {
        return if (password.length < 6)
            "Пароль должен содержать не менее 6 символов"
        else if (password.length > 12)
            "Пароль должен содержать не более 12 символов"
        else if (password.toCharArray().none { it.isDigit() })
            "Пароль должен содержать хотя бы одну цифру"
        else if (password.toCharArray().none { it.isLetter() })
            "Пароль должен содержать хотя бы одну букву"
        else
            null
    }
}