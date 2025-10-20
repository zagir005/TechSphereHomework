package com.zagirlek.user.mapper

import com.zagirlek.local.user.entitiy.UserEntity
import com.zagirlek.user.model.User

internal fun User.toEntity(): UserEntity = UserEntity(
    id = id,
    phone = phone,
    nickname = nickname,
    status = status,
    password = password
)

internal fun UserEntity.toDomain(): User = User(
    id = id,
    phone = phone,
    nickname = nickname,
    status = status,
    password = password
)

internal fun List<UserEntity>.toDomain(): List<User> = map {
    it.toDomain()
}