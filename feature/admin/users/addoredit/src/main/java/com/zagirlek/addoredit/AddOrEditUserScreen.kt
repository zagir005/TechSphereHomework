package com.zagirlek.addoredit

import com.zagirlek.addoredit.model.AddOrEditUserModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

interface AddOrEditUserScreen {
    val model: StateFlow<AddOrEditUserModel>
    fun nicknameEdit(value: String)
    fun passwordEdit(value: String)
    fun phoneEdit(value: String)
    fun toggleAdminStatus()
    fun saveUser()
    fun cancel()

    @Serializable
    data class AddOrEditUserConfig(val userId: Long?)
}