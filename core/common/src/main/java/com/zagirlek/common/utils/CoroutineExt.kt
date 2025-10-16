package com.zagirlek.common.utils

import kotlinx.coroutines.Job
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty

fun canceledJob(): ReadWriteProperty<Any?, Job?> =
    Delegates.observable(null) { _, oldValue, _ -> oldValue?.cancel() }