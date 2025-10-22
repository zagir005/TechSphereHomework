package com.zagirlek.list.model

import com.zagirlek.ui.elements.alertdialog.AlertDialogState
import com.zagirlek.user.model.User

data class UserListModel (
    val userList: List<User> = emptyList(),
    val searchField: String? = null,
    val alertDialogState: AlertDialogState? = null
)
