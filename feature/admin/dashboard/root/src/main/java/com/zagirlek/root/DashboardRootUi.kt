package com.zagirlek.root

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.zagirlek.home.DashboardHomeScreenUi
import com.zagirlek.list.ComputerListScreenUi
import com.zagirlek.ui.elements.navigationbar.Tab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardRootUi(
    component: DashboardRootComponent,
    topBar: ( @Composable (tabInfo: Tab) -> Unit ) -> Unit
) {
    Children(
        stack = component.stack
    ) {
        when(val child = it.instance){
            is DashboardRootComponent.Child.ComputersList -> ComputerListScreenUi(component = child.component)
            is DashboardRootComponent.Child.DashboardHome -> DashboardHomeScreenUi(component = child.component)
        }
    }
}