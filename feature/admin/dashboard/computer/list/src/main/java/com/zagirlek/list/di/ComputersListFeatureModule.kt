package com.zagirlek.list.di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.zagirlek.list.ComputerListScreen
import com.zagirlek.list.cmp.ComputerListScreenComponent

class ComputersListFeatureModule(
    private val storeFactory: StoreFactory,
){
    fun getComputersListScreen(
        context: ComponentContext,
    ): ComputerListScreen =
        ComputerListScreenComponent(
            storeFactory = storeFactory,
            componentContext = context
        )
}