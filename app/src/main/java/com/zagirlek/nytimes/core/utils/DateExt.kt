package com.zagirlek.nytimes.core.utils

import java.time.Duration
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


fun LocalDateTime.timeAgoOrDate(): String {
    val now = LocalDateTime.now()
    val duration = Duration.between(this, now)

    val days = duration.toDays()
    val hours = duration.toHours()
    val minutes = duration.toMinutes()

    return when {
        days > 30 -> this.format(DateTimeFormatter.ofPattern("dd.MM.yy")) + "г."
        days > 1 -> "$days дней назад"
        days == 1L -> "вчера"
        hours > 1 -> "$hours часов назад"
        hours == 1L -> "час назад"
        minutes > 1 -> "$minutes минут назад"
        minutes == 1L -> "минута назад"
        else -> "только что"
    }
}