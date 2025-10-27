package com.zagirlek.common.model

import com.zagirlek.common.crud.CrudDomainModel

data class User(
    val phone: String = "",
    val nickname: String = "",
    val status: UserType = UserType.CLIENT,
    val isBlocked: Boolean = false,
    val password: String = "",
    val id: Long = 0,
): CrudDomainModel {
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