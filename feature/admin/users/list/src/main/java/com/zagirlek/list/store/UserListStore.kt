package com.zagirlek.list.store

import com.arkivanov.mvikotlin.core.store.Store
import com.zagirlek.common.model.User
import com.zagirlek.list.store.UserListStore.Intent
import com.zagirlek.list.store.UserListStore.State
import com.zagirlek.ui.elements.alertdialog.AlertDialogState

interface UserListStore: Store<Intent, State, Nothing> {
    data class State (
        val currentUser: User? = null,
        val userList: List<User>? = null,
        val searchField: String? = null,
        val alertDialogState: AlertDialogState? = null
    )

    sealed class Intent {
        data class SearchFieldChange(val query: String?): Intent()
        data class DeleteUser(val userId: Long): Intent()
        data object DialogDismiss: Intent()
    }
}