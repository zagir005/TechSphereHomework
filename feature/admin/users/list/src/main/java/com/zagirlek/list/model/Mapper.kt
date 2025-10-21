package com.zagirlek.list.model

import com.zagirlek.list.store.UserStore

internal fun UserStore.State.toModel(): UserListModel = UserListModel(
    userList = userList,
    searchField = searchField
)