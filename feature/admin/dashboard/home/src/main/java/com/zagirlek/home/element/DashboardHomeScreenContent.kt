package com.zagirlek.home.element

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zagirlek.home.R
import com.zagirlek.ui.elements.TitleText

@Composable
internal fun DashboardHomeScreenContent(
    modifier: Modifier = Modifier,
    onNewSessionClick: () -> Unit = {},
    onComputersListClick: () -> Unit = {},
    onTariffsClick: () -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        DashboardMenu(
            onNewSessionClick = onNewSessionClick,
            onComputersListClick = onComputersListClick,
            onTariffsClick = onTariffsClick
        )
        HorizontalDivider()
        TitleText(text = stringResource(R.string.sessions))
    }
}