package com.zagirlek.local.computer.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.zagirlek.common.crud.CrudEntity

@Entity(
    tableName = "computers",
    indices = [
        Index(value = ["id"], unique = true),
        Index(value = ["code"], unique = true)
    ]
)
data class ComputerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(collate = ColumnInfo.NOCASE) val code: String,
    val description: String? = null,
): CrudEntity
