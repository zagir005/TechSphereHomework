package com.zagirlek.user.model

import com.zagirlek.common.model.UserType

data class User(
    val phone: String = "",
    val nickname: String = "",
    val status: UserType = UserType.CLIENT,
    val isBlocked: Boolean = false,
    val password: String = "",
    val id: Long = 0,
){
    companion object {
        fun exampleUser(
            phone: String = "89743214312",
            nickname: String = "User",
            status: UserType = UserType.ADMIN,
            password: String = "Password",
            id: Long = 0
        ): User = User(
            phone = phone,
            nickname = nickname,
            status = status,
            password = password,
            id = id
        )
    }
}
