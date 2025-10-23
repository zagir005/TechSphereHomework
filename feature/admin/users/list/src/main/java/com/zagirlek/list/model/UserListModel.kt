package com.zagirlek.list.model

import com.zagirlek.ui.elements.alertdialog.AlertDialogState
import com.zagirlek.user.model.User

data class UserListModel (
    val userList: List<User>? = null,
    val searchField: String? = null,
    val alertDialogState: AlertDialogState? = null
)
