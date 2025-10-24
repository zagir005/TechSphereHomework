package com.zagirlek.root.di

import com.arkivanov.decompose.ComponentContext
import com.zagirlek.root.DashboardRootComponent
import com.zagirlek.root.DefaultDashboardRootComponent

class DashboardRootFeatureModule {
    fun getDashboardRootComponent(
        context: ComponentContext
    ): DashboardRootComponent =
        DefaultDashboardRootComponent(
            componentContext = context
        )
}