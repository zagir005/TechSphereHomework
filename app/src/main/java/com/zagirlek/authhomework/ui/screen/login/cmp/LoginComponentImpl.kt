package com.zagirlek.authhomework.ui.screen.login.cmp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.zagirlek.authhomework.ui.screen.login.cmp.reducer.LoginReducer
import com.zagirlek.authhomework.ui.screen.login.cmp.state.LoginAction
import com.zagirlek.authhomework.ui.screen.login.cmp.state.LoginState
import com.zagirlek.authhomework.ui.screen.root.components.LoginComponent

class LoginComponentImpl(
    componentContext: ComponentContext
): ComponentContext by componentContext, LoginComponent {
    private val reducer = LoginReducer()

    private val _state = MutableValue(LoginState())
    override val state: Value<LoginState> = _state

    override fun action(loginAction: LoginAction){
        when(loginAction){
            is LoginAction.LoginTextChanged -> {
                _state.update {
                    reducer.reduce(_state.value, loginAction)
                }
            }
            is LoginAction.PasswordTextChanged -> {
                _state.update {
                    reducer.reduce(_state.value, loginAction)
                }
            }
            LoginAction.Submit -> {
                _state.update {
                    reducer.reduce(_state.value, loginAction)
                }
            }
        }
    }
}