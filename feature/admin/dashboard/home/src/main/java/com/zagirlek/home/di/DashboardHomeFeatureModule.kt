package com.zagirlek.home.di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.zagirlek.home.DashboardHomeScreen
import com.zagirlek.home.cmp.DashboardHomeScreenComponent

class DashboardHomeFeatureModule(
    storeFactory: StoreFactory
) {
    fun getHomeComponent(
        componentContext: ComponentContext,
        toComputersList: () -> Unit,
        toTariffsList: () -> Unit,
    ): DashboardHomeScreen =
        DashboardHomeScreenComponent(
            componentContext = componentContext,
            toComputersList = toComputersList,
            toTariffsList = toTariffsList
        )
}