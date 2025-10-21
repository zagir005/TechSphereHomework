package com.zagirlek.add.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.zagirlek.add.model.toUser
import com.zagirlek.add.store.AddUserStore.Intent
import com.zagirlek.add.store.AddUserStore.State
import com.zagirlek.user.usecase.AddUserUseCase
import kotlinx.coroutines.launch

class AddUserStoreFactory(
    private val storeFactory: StoreFactory,
    private val addUserUseCase: AddUserUseCase
) {
    fun create(): AddUserStore = object: AddUserStore, Store<Intent, State, Nothing> by storeFactory.create(
        name = "add_or_edit_user_store",
        initialState = State(),
        executorFactory = ::ExecutorImpl,
        reducer = ReducerImpl
    ){ }
    private sealed interface Action {
        data object SaveUser: Action
    }
    private sealed class Msg{
        data class Nickname(val value: String): Msg()
        data class Password(val value: String): Msg()
        data class Phone(val value: String): Msg()
        data class IsAdmin(val value: Boolean): Msg()
    }
    private inner class ExecutorImpl: CoroutineExecutor<Intent, Action, State, Msg, Nothing>() {
        override fun executeAction(action: Action) {
            when(action){
                is Action.SaveUser -> {
                    scope.launch {
                        addUserUseCase(state().toUser())
                    }
                }
            }
        }

        override fun executeIntent(intent: Intent) {
            when(intent){
                is Intent.EditNickname -> dispatch(Msg.Nickname(intent.value))
                is Intent.EditPassword -> dispatch(Msg.Password(intent.value))
                is Intent.EditPhone -> dispatch(Msg.Phone(intent.value))
                Intent.Save -> forward(Action.SaveUser)
                Intent.ToggleIsAdminStatus -> dispatch(Msg.IsAdmin(!state().isAdmin))
            }
        }
    }

    private object ReducerImpl: Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when(msg){
            is Msg.Nickname -> copy(nicknameTextField = nicknameTextField.copy(msg.value))
            is Msg.Password -> copy(passwordTextField = passwordTextField.copy(msg.value))
            is Msg.Phone -> copy(phoneTextField = phoneTextField.copy(msg.value))
            is Msg.IsAdmin -> copy(isAdmin = msg.value)
        }
    }
}