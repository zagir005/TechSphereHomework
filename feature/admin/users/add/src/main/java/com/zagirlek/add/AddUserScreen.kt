package com.zagirlek.add

import com.zagirlek.add.model.AddUserModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

interface AddUserScreen {
    val model: StateFlow<AddUserModel>
    fun loginEdit(value: String)
    fun passwordEdit(value: String)
    fun phoneEdit(value: String)
    fun toggleAdminStatus()

    @Serializable
    data object AddUserConfig
}