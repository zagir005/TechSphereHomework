package com.zagirlek.list.cmp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.zagirlek.list.ComputerListScreen

class ComputerListScreenComponent(
    storeFactory: StoreFactory,
    componentContext: ComponentContext
): ComputerListScreen, ComponentContext by componentContext {
    override fun addComputer() {

    }

    override fun editComputer(userId: Long) {
    }

    override fun hideAddOrEditComputerDialog() {
    }

    override fun deleteComputer(userId: Long) {

    }
}