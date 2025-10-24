package com.zagirlek.root

import androidx.compose.runtime.Composable
import com.zagirlek.ui.elements.navigationbar.Tab

@Composable
fun DashboardRootUi(
    component: DashboardRootComponent,
    topBar: ( @Composable (tabInfo: Tab) -> Unit ) -> Unit
) {
    topBar{

    }
}