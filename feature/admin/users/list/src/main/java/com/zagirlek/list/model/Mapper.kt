package com.zagirlek.list.model

import com.zagirlek.list.store.UserListStore

internal fun UserListStore.State.toModel(): UserListModel = UserListModel(
    userList = userList,
    searchField = searchField,
    alertDialogState = alertDialogState
)