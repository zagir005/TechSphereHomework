package com.zagirlek.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.zagirlek.list.UserListScreenUi
import com.zagirlek.ui.R
import com.zagirlek.ui.elements.navigationbar.AppNavigationBar
import com.zagirlek.ui.elements.navigationbar.Tab

internal data object UserList: Tab(
    nameResource = R.string.users,
    iconResource = R.drawable.ic_users
)
internal data object Dashboard: Tab(
    nameResource = R.string.dashboard,
    iconResource = R.drawable.ic_dashboard
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminRootUi(
    component: AdminRootComponent
) {
    val pages by component.pages.subscribeAsState()
    val tabItems = listOf(UserList, Dashboard)
    val navigationBarHeight = 75.dp
    var currTab by remember { mutableStateOf<@Composable (Tab) -> Unit>({}) }

    Scaffold(
        topBar = {
            currTab(
                tabItems[pages.selectedIndex]
            )
        },
        bottomBar = {
            AppNavigationBar(
                tabs = tabItems,
                selected = pages.selectedIndex,
                modifier = Modifier
                    .height(navigationBarHeight)
            ) { selectedIndex ->
                component.selectPage(selectedIndex)
            }
        }
    ){ paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
        ){
            when(val child = pages.items[pages.selectedIndex].instance!!) {
                is AdminRootComponent.Child.Dashboard ->
                    DashboardRootUi(child.component) {
                        currTab = it
                    }

                is AdminRootComponent.Child.UserList ->
                    UserListScreenUi(child.component) {
                        currTab = it
                    }
            }
        }
    }
}