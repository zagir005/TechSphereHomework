package com.zagirlek.root.di

import com.arkivanov.decompose.ComponentContext
import com.zagirlek.home.di.DashboardHomeFeatureModule
import com.zagirlek.list.di.ComputersListFeatureModule
import com.zagirlek.root.DashboardRootComponent
import com.zagirlek.root.cmp.DefaultDashboardRootComponent


class DashboardRootFeatureModule(
    private val dashboardHomeModule: DashboardHomeFeatureModule,
    private val computersListModule: ComputersListFeatureModule
) {
    fun getDashboardRootComponent(
        context: ComponentContext
    ): DashboardRootComponent =
        DefaultDashboardRootComponent(
            componentContext = context,
            dashboardHomeModule = dashboardHomeModule,
            computersListModule = computersListModule
        )
}