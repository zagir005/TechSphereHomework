package com.zagirlek.nytimes.ui.screen.login.cmp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.zagirlek.nytimes.core.base.component.BaseComponent
import com.zagirlek.nytimes.domain.repository.AuthRepository
import com.zagirlek.nytimes.ui.screen.login.LoginComponent
import com.zagirlek.nytimes.ui.screen.login.cmp.reducer.LoginMutation
import com.zagirlek.nytimes.ui.screen.login.cmp.reducer.LoginReducer
import com.zagirlek.nytimes.ui.screen.login.cmp.state.LoginAction
import com.zagirlek.nytimes.ui.screen.login.cmp.state.LoginState
import kotlinx.coroutines.launch

class DefaultLoginComponent(
    componentContext: ComponentContext,
    private val authRepository: AuthRepository,
    private val onAuth: () -> Unit
) : BaseComponent<LoginState, LoginAction, LoginMutation, LoginReducer>(
    componentContext = componentContext,
    reducer = LoginReducer()
), LoginComponent {
    private val stateHolder = instanceKeeper.getOrCreate(HolderKey) { StateHolder() }
    private val _state get() = stateHolder.state
    override val state: Value<LoginState> get() = _state

    override fun action(action: LoginAction) {
        when (action) {
            is LoginAction.LoginTextChanged -> {
                _state.update {
                    LoginMutation.LoginTextChanged(action.text).reduce(_state.value)
                }
            }

            is LoginAction.PasswordTextChanged -> {
                _state.update {
                    LoginMutation.PasswordTextChanged(action.text).reduce(_state.value)
                }
            }

            LoginAction.Submit -> {
                componentScope.launch {
                    login()
                }
            }

            LoginAction.ContinueWithoutAuth -> {
                onContinueWithoutAuth()
            }
        }
    }

    fun onContinueWithoutAuth() {
        onAuth()
    }

    suspend fun login(){
        onAuth()
    }


    private class StateHolder : InstanceKeeper.Instance {
        val state: MutableValue<LoginState> = MutableValue(LoginState())
    }

    private object HolderKey
}