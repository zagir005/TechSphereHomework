package com.zagirlek.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.zagirlek.list.UserListScreenUi
import com.zagirlek.ui.elements.navigationbar.AppNavigationBar
import com.zagirlek.ui.elements.navigationbar.Tab

internal data object UserList: Tab(
    nameResource = com.zagirlek.ui.R.string.users,
    iconResource = com.zagirlek.ui.R.drawable.ic_users
)
internal data object Dashboard: Tab(
    nameResource = com.zagirlek.ui.R.string.dashboard,
    iconResource = com.zagirlek.ui.R.drawable.ic_dashboard
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminRootUi(
    component: AdminRootComponent
) {
    val pages by component.pages.subscribeAsState()
    val tabItems = listOf(UserList, Dashboard)
    val navigationBarHeight = 75.dp

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(tabItems[pages.selectedIndex].nameResource)
                    )
                }
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
            when(val child = pages.items[pages.selectedIndex].instance!!){
                is AdminRootComponent.Child.Dashboard ->
                    DashboardRootUi(child.component)
                is AdminRootComponent.Child.UserList ->
                    UserListScreenUi(child.component)
            }
        }
    }
}