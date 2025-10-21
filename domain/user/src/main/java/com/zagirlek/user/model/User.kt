package com.zagirlek.user.model

import com.zagirlek.common.model.UserStatus

data class User(
    val phone: String,
    val nickname: String,
    val status: UserStatus,
    val password: String,
    val id: Long = 0,
){
    companion object{
        fun exampleUser(
            phone: String = "89743214312",
            nickname: String = "User",
            status: UserStatus = UserStatus.ADMIN,
            password: String = "Password"
        ): User = User(
                phone = phone,
                nickname = nickname,
                status = status,
                password = password
            )
    }
}
