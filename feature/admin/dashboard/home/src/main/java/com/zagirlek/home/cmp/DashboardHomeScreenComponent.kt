package com.zagirlek.home.cmp

import com.arkivanov.decompose.ComponentContext
import com.zagirlek.home.DashboardHomeScreen

class DashboardHomeScreenComponent(
    componentContext: ComponentContext,
    private val toComputersList: () -> Unit,
    private val toTariffsList: () -> Unit,
): DashboardHomeScreen, ComponentContext by componentContext{

    override fun createSession() {

    }

    override fun computers() =
        toComputersList()

    override fun tariffs() =
        toTariffsList()

}