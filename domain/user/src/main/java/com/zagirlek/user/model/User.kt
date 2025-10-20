package com.zagirlek.user.model

import com.zagirlek.common.model.UserStatus

data class User(
    val id: Long = 0,
    val phone: String,
    val nickname: String,
    val status: UserStatus,
    val password: String
)
