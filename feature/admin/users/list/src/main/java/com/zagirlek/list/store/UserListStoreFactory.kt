package com.zagirlek.list.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.zagirlek.common.model.User
import com.zagirlek.list.store.UserListStore.Intent
import com.zagirlek.list.store.UserListStore.State
import com.zagirlek.list.store.UserListStoreFactory.Action.LoadUserFlow
import com.zagirlek.list.store.UserListStoreFactory.Msg.SearchField
import com.zagirlek.ui.elements.alertdialog.AlertDialogState
import com.zagirlek.ui.elements.alertdialog.DialogButton
import com.zagirlek.user.usecase.DeleteUserByIdUseCase
import com.zagirlek.user.usecase.GetUsersWithCurrentUserFlowUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class UserListStoreFactory(
    private val storeFactory: StoreFactory,
    private val deleteUserByIdUseCase: DeleteUserByIdUseCase,
    private val getUsersWithCurrentUserFlowUseCase: GetUsersWithCurrentUserFlowUseCase
) {
    fun create(): UserListStore = object: UserListStore, Store<Intent, State, Nothing> by storeFactory.create(
        name = "user_store",
        initialState = State(),
        bootstrapper = SimpleBootstrapper(LoadUserFlow()),
        executorFactory = ::ExecutorImpl,
        reducer = ReducerImpl
    ){ }

    private sealed class Action{
        data class LoadUserFlow(val searchQuery: String? = null): Action()
    }

    private inner class ExecutorImpl: CoroutineExecutor<Intent, Action, State, Msg, Nothing>() {
        override fun executeIntent(intent: Intent) {
            when(intent){
                is Intent.SearchFieldChange -> {
                    dispatch(SearchField(intent.query))
                    executeAction(LoadUserFlow(intent.query))
                }
                is Intent.DeleteUser -> {
                    dispatch(
                        Msg.AlertDialog(
                        alertDialogState = deleteUserAlertDialog(
                            onConfirm = {
                                scope.launch(Dispatchers.IO) {
                                    deleteUserByIdUseCase(intent.userId)
                                }
                            }
                        )
                    ))
                }
                Intent.DialogDismiss -> hideAlertDialog()
            }
        }
        override fun executeAction(action: Action){
            when(action){
                is LoadUserFlow -> {
                    scope.launch {
                        getUsersWithCurrentUserFlowUseCase(action.searchQuery)
                            .onSuccess {
                                combine(
                                    it.current,
                                    it.allUsersList
                                ) { current, allUsersList ->
                                    Pair(current, allUsersList)
                                }.collect { flowPair ->
                                    dispatch(Msg.CurrentUser(user = flowPair.first))
                                    dispatch(Msg.UserList(userList = flowPair.second))
                                }
                            }
                    }
                }
            }
        }
        private fun deleteUserAlertDialog(
            onConfirm: () -> Unit = {},
            onCancel: () -> Unit = {},
        ): AlertDialogState =
            AlertDialogState(
                title = "Удалить пользователя?",
                message = "Отменить действие будет невозможно",
                confirmButton = DialogButton(
                    text = "Удалить",
                    onClick ={
                        onConfirm()
                        hideAlertDialog()
                    }

                ),
                cancelButton = DialogButton(
                    text = "Отмена",
                    onClick = {
                        onCancel()
                        hideAlertDialog()
                    }
                )
            )
        private fun hideAlertDialog(){
            dispatch(Msg.AlertDialog(null))
        }
    }

    private sealed class Msg {
        data class UserList(val userList: List<User>): Msg()
        data class SearchField(val query: String?): Msg()
        data class AlertDialog(val alertDialogState: AlertDialogState?): Msg()
        data class CurrentUser(val user: User?): Msg()
    }

    private object ReducerImpl: Reducer<State, Msg>{
        override fun State.reduce(msg: Msg): State = when(msg){
            is Msg.UserList -> copy(userList = msg.userList)
            is SearchField -> copy(searchField = msg.query)
            is Msg.AlertDialog -> copy(alertDialogState = msg.alertDialogState)
            is Msg.CurrentUser -> copy(currentUser = msg.user)
        }
    }
}