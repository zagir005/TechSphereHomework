package com.zagirlek.nytimes.core.base.reducer

fun interface Reducer<VS: ViewState, M: Mutation>{
    fun reduce(state: VS, mutation: M): VS
}