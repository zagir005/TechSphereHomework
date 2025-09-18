package com.zagirlek.nytimes.ui.screen.login.cmp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.zagirlek.nytimes.ui.screen.login.LoginComponent
import com.zagirlek.nytimes.ui.screen.login.cmp.reducer.LoginReducer
import com.zagirlek.nytimes.ui.screen.login.cmp.state.LoginAction
import com.zagirlek.nytimes.ui.screen.login.cmp.state.LoginState

class DefaultLoginComponent(
    componentContext: ComponentContext,
    private val onAuth: () -> Unit
): ComponentContext by componentContext, LoginComponent {
    private val stateHolder = instanceKeeper.getOrCreate(HolderKey){ StateHolder() }
    override val state: Value<LoginState> = stateHolder._state

    override fun action(action: LoginAction){
        with(stateHolder){
            when(action){
                is LoginAction.LoginTextChanged -> {
                    _state.update {
                        reducer.reduce(_state.value, action)
                    }
                }
                is LoginAction.PasswordTextChanged -> {
                    _state.update {
                        reducer.reduce(_state.value, action)
                    }
                }
                LoginAction.Submit -> {
                    _state.update {
                        reducer.reduce(_state.value, action)
                    }
                }
                LoginAction.ContinueWithoutAuth -> {
                    onAuth()
                }
            }
        }
    }

    private class StateHolder: InstanceKeeper.Instance {
        val _state: MutableValue<LoginState> = MutableValue(LoginState())
        val reducer: LoginReducer = LoginReducer()
    }
    private object HolderKey
}