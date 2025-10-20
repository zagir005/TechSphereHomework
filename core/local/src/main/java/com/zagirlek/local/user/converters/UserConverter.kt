package com.zagirlek.local.user.converters

import androidx.room.TypeConverter
import com.zagirlek.common.model.NewsCategory
import com.zagirlek.common.model.UserStatus

internal class UserConverter {
    @TypeConverter
    fun toStatus(value: String): UserStatus = enumValueOf(name = value)

    @TypeConverter
    fun fromStatus(value: NewsCategory): String = value.name
}