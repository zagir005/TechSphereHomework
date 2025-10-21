package com.zagirlek.local.user.entitiy

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.zagirlek.common.model.UserStatus

@Entity(
    tableName = "users",
    indices = [Index(value = ["phone"], unique = true),
        Index(value = ["nickname"], unique = true)]
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val phone: String,
    val nickname: String,
    val status: UserStatus,
    val isBlocked: Boolean = false,
    val password: String
)