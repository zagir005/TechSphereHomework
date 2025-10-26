package com.zagirlek.local.computer.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "computers",
    indices = [
        Index(value = ["id"], unique = true),
        Index(value = ["code"], unique = true)
    ]
)
data class ComputerEntity(
    @ColumnInfo(collate = ColumnInfo.NOCASE) val code: String,
    val description: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
