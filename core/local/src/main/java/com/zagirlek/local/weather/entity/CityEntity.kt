package com.zagirlek.local.weather.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "city",
    indices = [Index(value = ["name"], unique = true)]
)
data class CityEntity(
    @ColumnInfo(collate = ColumnInfo.NOCASE) val name: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)
