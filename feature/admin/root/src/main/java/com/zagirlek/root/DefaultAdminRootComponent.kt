package com.zagirlek.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.router.pages.Pages
import com.arkivanov.decompose.router.pages.PagesNavigation
import com.arkivanov.decompose.router.pages.childPages
import com.arkivanov.decompose.router.pages.select
import com.arkivanov.decompose.value.Value
import com.zagirlek.list.UserListScreen
import com.zagirlek.list.di.UserListFeatureModule
import com.zagirlek.root.AdminRootComponent.Child.Dashboard
import com.zagirlek.root.AdminRootComponent.Child.UserList
import com.zagirlek.root.di.DashboardRootFeatureModule
import kotlinx.serialization.Serializable

class DefaultAdminRootComponent(
    componentContext: ComponentContext,
    private val dashboardRootFeatureModule: DashboardRootFeatureModule,
    private val userListFeatureModule: UserListFeatureModule,
    private val logout: () -> Unit,
): AdminRootComponent, ComponentContext by componentContext {
    private val navigation = PagesNavigation<Config>()
    override val pages: Value<ChildPages<Config, AdminRootComponent.Child>> = childPages(
        source = navigation,
        serializer = Config.serializer(),
        initialPages = {
            Pages(
                items = listOf(
                    Config.UserList,
                    Config.Dashboard
                ),
                selectedIndex = 0
            )
        },
        childFactory = ::child,
    )

    override fun selectPage(index: Int) {
        navigation.select(index)
    }

    private fun child(config: Config, component: ComponentContext): AdminRootComponent.Child =
        when(config){
            Config.Dashboard -> Dashboard(dashboard(component))
            Config.UserList -> UserList(userList(component))
        }

    private fun dashboard(context: ComponentContext): DashboardRootComponent =
        dashboardRootFeatureModule.getDashboardRootComponent(context)


    private fun userList(context: ComponentContext): UserListScreen =
        userListFeatureModule.getUserListScreen(
            context = context,
            logout = logout
        )

    @Serializable
    sealed class Config{
        @Serializable
        object UserList: Config()
        @Serializable
        object Dashboard: Config()
    }
}