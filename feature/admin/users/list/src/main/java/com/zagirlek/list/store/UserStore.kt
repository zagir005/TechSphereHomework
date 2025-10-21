package com.zagirlek.list.store

import com.arkivanov.mvikotlin.core.store.Store
import com.zagirlek.list.store.UserStore.Intent
import com.zagirlek.list.store.UserStore.State
import com.zagirlek.user.model.User

interface UserStore: Store<Intent, State, Nothing> {
    data class State (
        val userList: List<User> = emptyList(),
        val searchField: String? = null
    )

    sealed class Intent {
        data class SearchFieldChange(val query: String?): Intent()
    }
}