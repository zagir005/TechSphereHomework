package com.zagirlek.list

import com.zagirlek.list.model.UserListModel
import kotlinx.coroutines.flow.StateFlow

interface UserListScreen {
    val model: StateFlow<UserListModel>
    fun search(query: String)
    fun showDetails(userId: Long)
    fun hideDetails()
}