package com.zagirlek.common.basemvi.reducer

fun interface Reducer<VS: ViewState, M: Mutation>{
    fun reduce(state: VS, mutation: M): VS
}