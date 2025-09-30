package com.zagirlek.nytimes.core.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private val apiFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

fun String.toLocalDateTime(): LocalDateTime =
    LocalDateTime.parse(this, apiFormatter)

fun LocalDateTime.toEpochMillis(zoneId: ZoneId = ZoneId.systemDefault()): Long =
    this.atZone(zoneId).toInstant().toEpochMilli()

fun Long.toLocalDateTime(zoneId: ZoneId = ZoneId.systemDefault()): LocalDateTime =
    LocalDateTime.ofInstant(Instant.ofEpochMilli(this), zoneId)