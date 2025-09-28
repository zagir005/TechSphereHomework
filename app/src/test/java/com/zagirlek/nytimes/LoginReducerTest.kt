package com.zagirlek.nytimes

import com.zagirlek.nytimes.ui.screen.auth.cmp.reducer.LoginMutation
import com.zagirlek.nytimes.ui.screen.auth.cmp.reducer.LoginReducer
import com.zagirlek.nytimes.ui.screen.auth.cmp.state.AuthState
import com.zagirlek.nytimes.ui.screen.auth.cmp.state.textfield.TextFieldState
import com.zagirlek.nytimes.ui.screen.auth.cmp.state.textfield.textfielderror.LoginTextFieldError
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlin.test.Test
import kotlin.test.assertEquals

class LoginReducerTest {
    private val reducer = LoginReducer()

    @Test
    fun buttonEnabledCheck() {
        //Проверяем включается ли кнопка при валидном логине и пароле
        val initialState = AuthState(
            loginTextFieldState = TextFieldState(),
            passwordTextFieldState = TextFieldState(value = "Pass123")
        )

        val action = LoginMutation.LoginTextChanged("Логин_Юзера")
        val newState = reducer.reduce(initialState, action)

        assertEquals("Логин_Юзера", newState.loginTextFieldState.value)
        assertEquals(null, newState.loginTextFieldState.error)
        assertTrue(newState.buttonEnabled)
    }

    @Test
    fun onlyCyrillicErrorCheck() {
        //Проверяем выдачу ошибки OnlyCyrillic
        val initialState = AuthState()
        val action = LoginMutation.LoginTextChanged("Login123")
        val newState = reducer.reduce(initialState, action)

        assertEquals("Login123", newState.loginTextFieldState.value)
        assertEquals(LoginTextFieldError.OnlyCyrillic, newState.loginTextFieldState.error)
        assertFalse(newState.buttonEnabled)
    }

    @Test
    fun wrongLoginErrorCheck() {
        //Проверяем выдачу ошибки WrongLogin
        val initialState = AuthState()
        val action = LoginMutation.LoginTextChanged("Другая_учетка")
        val newState = reducer.reduce(initialState, action)

        assertEquals("Другая_учетка", newState.loginTextFieldState.value)
        assertEquals(LoginTextFieldError.WrongLogin, newState.loginTextFieldState.error)
        assertFalse(newState.buttonEnabled)
    }

    @Test
    fun enabledButtonWithCorrectPassword() {
        // проверяем включение кнопки при вводе валидного пароля
        val initialState = AuthState(
            loginTextFieldState = TextFieldState(value = "Логин_Юзера")
        )

        val action = LoginMutation.PasswordTextChanged("Pass123")
        val newState = reducer.reduce(initialState, action)

        assertEquals("Pass123", newState.passwordTextFieldState.value)
        assertEquals(null, newState.passwordTextFieldState.error)
        assertTrue(newState.buttonEnabled)
    }
}