package com.zagirlek.list.store

import com.arkivanov.mvikotlin.core.store.Store
import com.zagirlek.list.store.UserListStore.Intent
import com.zagirlek.list.store.UserListStore.State
import com.zagirlek.ui.elements.alertdialog.AlertDialogState
import com.zagirlek.user.model.User

interface UserListStore: Store<Intent, State, Nothing> {
    data class State (
        val userList: List<User> = emptyList(),
        val searchField: String? = null,
        val alertDialogState: AlertDialogState? = null
    )

    sealed class Intent {
        data class SearchFieldChange(val query: String?): Intent()
        data class DeleteUser(val userId: Long): Intent()
        data object DialogDismiss: Intent()
    }
}