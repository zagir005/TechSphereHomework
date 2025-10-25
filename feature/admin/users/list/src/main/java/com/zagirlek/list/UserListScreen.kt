package com.zagirlek.list

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.zagirlek.addoredit.AddOrEditUserScreen
import com.zagirlek.list.model.UserListModel
import kotlinx.coroutines.flow.StateFlow

interface UserListScreen {
    val model: StateFlow<UserListModel>
    val dialog: Value<ChildSlot<*, AddOrEditUserScreen>>
    fun search(query: String)
    fun addUser()
    fun editUser(userId: Long)
    fun hideAddEditUserDialog()
    fun deleteUser(userId: Long)
    fun onDialogDismiss()
    fun logout()
}