package com.zagirlek.common.basemvi.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.zagirlek.common.basemvi.reducer.Mutation
import com.zagirlek.common.basemvi.reducer.Reducer
import com.zagirlek.common.basemvi.reducer.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class BaseComponent<VS: ViewState, A: Action, M: Mutation, R: Reducer<VS, M>>(
    private val componentContext: ComponentContext,
    private val reducer: R
): ComponentContext by componentContext {

    protected val componentScope = coroutineScope(context = SupervisorJob() + Dispatchers.Main.immediate).also {
        doOnDestroy { it.cancel() }
    }

    protected fun M.reduce(state: VS): VS = reducer.reduce(state, this)

    abstract fun action(action: A)
}