package com.zagirlek.list.model

import com.zagirlek.common.model.User
import com.zagirlek.ui.elements.alertdialog.AlertDialogState

data class UserListModel (
    val userList: List<User>? = null,
    val searchField: String? = null,
    val alertDialogState: AlertDialogState? = null
)
