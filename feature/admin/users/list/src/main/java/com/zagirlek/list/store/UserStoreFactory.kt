package com.zagirlek.list.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.zagirlek.list.store.UserStore.Intent
import com.zagirlek.list.store.UserStore.State
import com.zagirlek.user.model.User
import com.zagirlek.user.usecase.GetUserListFlowUseCase
import kotlinx.coroutines.launch

class UserStoreFactory(
    private val storeFactory: StoreFactory,
    private val userListFlow: GetUserListFlowUseCase
) {
    fun create(): UserStore = object: UserStore, Store<Intent, State, Nothing> by storeFactory.create(
        name = "user_store",
        initialState = State(),
        bootstrapper = SimpleBootstrapper(Action.LoadUserFlow()),
        executorFactory = ::ExecutorImpl,
        reducer = ReducerImpl
    ){ }

    private sealed class Action{
        data class LoadUserFlow(val searchQuery: String? = null): Action()
    }

    private inner class ExecutorImpl: CoroutineExecutor<Intent, Action, State, Msg, Nothing>() {
        override fun executeIntent(intent: Intent) = when(intent){
            is Intent.SearchFieldChange -> {
                dispatch(Msg.SearchField(intent.query))
                executeAction(Action.LoadUserFlow(intent.query))
            }
        }

        override fun executeAction(action: Action){
            when(action){
                is Action.LoadUserFlow -> {
                    scope.launch {
                        userListFlow(action.searchQuery).collect {
                            dispatch(Msg.UserList(userList = it))
                        }
                    }
                }
            }
        }
    }

    private sealed class Msg {
        data class UserList(val userList: List<User>): Msg()
        data class SearchField(val query: String?): Msg()
    }

    private object ReducerImpl: Reducer<State, Msg>{
        override fun State.reduce(msg: Msg): State = when(msg){
            is Msg.UserList -> copy(userList = msg.userList)
            is Msg.SearchField -> copy(searchField = msg.query)
        }
    }
}