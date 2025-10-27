package com.zagirlek.common.model

import com.zagirlek.common.crud.CrudDomainModel

data class Computer (
    val code: String,
    val description: String? = null,
    val id: Long = 0
): CrudDomainModel