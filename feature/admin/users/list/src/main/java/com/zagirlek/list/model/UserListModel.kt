package com.zagirlek.list.model

import com.zagirlek.user.model.User

data class UserListModel (
    val userList: List<User> = emptyList(),
    val searchField: String? = null
)
