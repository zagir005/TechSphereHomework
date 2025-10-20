package com.zagirlek.root.di

import com.arkivanov.decompose.ComponentContext
import com.zagirlek.list.di.UserListFeatureModule
import com.zagirlek.root.AdminRootComponent
import com.zagirlek.root.DefaultAdminRootComponent

class AdminRootFeatureModule(
    private val dashboardRootFeatureModule: DashboardRootFeatureModule,
    private val userListFeatureModule: UserListFeatureModule
) {
    fun getAdminRootComponent(
        componentContext: ComponentContext
    ): AdminRootComponent =
        DefaultAdminRootComponent(
            componentContext = componentContext,
            dashboardRootFeatureModule = dashboardRootFeatureModule,
            userListFeatureModule = userListFeatureModule
        )
}