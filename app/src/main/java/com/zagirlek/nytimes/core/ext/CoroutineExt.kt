package com.zagirlek.nytimes.core.ext

import kotlinx.coroutines.Job
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty

fun canceledJob(): ReadWriteProperty<Any?, Job?> =
    Delegates.observable(null) { _, oldValue, _ -> oldValue?.cancel() }