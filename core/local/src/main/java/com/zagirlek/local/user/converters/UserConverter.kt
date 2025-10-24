package com.zagirlek.local.user.converters

import androidx.room.TypeConverter
import com.zagirlek.common.model.NewsCategory
import com.zagirlek.common.model.UserType

internal class UserConverter {
    @TypeConverter
    fun toStatus(value: String): UserType = enumValueOf(name = value)

    @TypeConverter
    fun fromStatus(value: NewsCategory): String = value.name
}