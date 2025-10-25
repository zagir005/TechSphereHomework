package com.zagirlek.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.zagirlek.home.DashboardHomeScreen
import com.zagirlek.list.ComputerListScreen

interface DashboardRootComponent{
    val stack: Value<ChildStack<*, Child>>
    sealed class Child{
        data class DashboardHome(val component: DashboardHomeScreen): Child()
        data class ComputersList(val component: ComputerListScreen): Child()
    }
}