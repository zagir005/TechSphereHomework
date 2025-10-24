package com.zagirlek.common.utils

inline fun <T> List<T>.ifNotEmpty(block: (List<T>) -> Unit){
    if (isNotEmpty()) block(this)
}