package com.zagirlek.nytimes.core.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class CityEntity(
    val name: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)
