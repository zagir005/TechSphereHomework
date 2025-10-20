package com.zagirlek.root

import com.arkivanov.decompose.ComponentContext

class DefaultDashboardRootComponent(
    componentContext: ComponentContext
): DashboardRootComponent, ComponentContext by componentContext {
}