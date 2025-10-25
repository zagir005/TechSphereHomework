package com.zagirlek.home

import androidx.compose.runtime.Composable
import com.zagirlek.home.element.DashboardHomeScreenContent

@Composable
fun DashboardHomeScreenUi(
    component: DashboardHomeScreen
) {
    DashboardHomeScreenContent(
        onNewSessionClick = { component.createSession() },
        onComputersListClick = { component.computers() },
        onTariffsClick = { component.tariffs() }
    )
}