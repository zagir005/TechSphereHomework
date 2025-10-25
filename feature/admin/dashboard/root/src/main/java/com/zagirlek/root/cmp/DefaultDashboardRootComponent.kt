package com.zagirlek.root.cmp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.zagirlek.home.DashboardHomeScreen
import com.zagirlek.home.di.DashboardHomeFeatureModule
import com.zagirlek.list.ComputerListScreen
import com.zagirlek.list.di.ComputersListFeatureModule
import com.zagirlek.root.DashboardRootComponent
import kotlinx.serialization.Serializable

class DefaultDashboardRootComponent(
    componentContext: ComponentContext,
    private val dashboardHomeModule: DashboardHomeFeatureModule,
    private val computersListModule: ComputersListFeatureModule
): DashboardRootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()
    override val stack: Value<ChildStack<*, DashboardRootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.DashboardHome,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(config: Config, componentContext: ComponentContext): DashboardRootComponent.Child{
        return when(config) {
            Config.ComputersList -> DashboardRootComponent.Child.ComputersList(
                component = computersList(
                    componentContext = componentContext
                )
            )

            Config.DashboardHome -> DashboardRootComponent.Child.DashboardHome(
                component = dashboardHome(
                    componentContext = componentContext
                )
            )
        }
    }
    private fun computersList(componentContext: ComponentContext): ComputerListScreen =
        computersListModule.getComputersListScreen(
            context = componentContext
        )
    private fun dashboardHome(componentContext: ComponentContext): DashboardHomeScreen =
        dashboardHomeModule.getHomeComponent(
            componentContext = componentContext,
            toComputersList = {
                navigation.push(Config.ComputersList)
            },
            toTariffsList = {

            }
        )

    @Serializable
    private sealed class Config{
        @Serializable
        object DashboardHome: Config()
        @Serializable
        object ComputersList: Config()
//        @Serializable
//        object Tariffs: Config()
    }
}