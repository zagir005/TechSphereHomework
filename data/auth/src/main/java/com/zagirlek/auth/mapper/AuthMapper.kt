package com.zagirlek.auth.mapper

import com.zagirlek.auth.model.AuthToken
import com.zagirlek.common.model.User
import com.zagirlek.common.model.UserType
import com.zagirlek.local.user.entitiy.UserEntity

internal fun UserEntity.toAuthToken(): AuthToken = AuthToken(
    userId = id,
    tokenType = when (type) {
        UserType.ADMIN -> AuthToken.TokenType.ADMIN
        UserType.CLIENT -> AuthToken.TokenType.CLIENT
    }
)

internal fun UserEntity.toUser(): User = User(
    id = id,
    phone = phone,
    nickname = nickname,
    status = type,
    password = password
)