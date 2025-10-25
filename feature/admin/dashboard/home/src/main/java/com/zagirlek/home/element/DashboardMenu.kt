package com.zagirlek.home.element

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zagirlek.ui.R
import com.zagirlek.ui.elements.AppCardFilled
import com.zagirlek.ui.elements.NyTimesPreview

@Composable
internal fun DashboardMenu(
    modifier: Modifier = Modifier,
    onNewSessionClick: () -> Unit = {},
    onComputersListClick: () -> Unit = {},
    onTariffsClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(2f)
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DashboardMenuButton(
            iconPainter = painterResource(R.drawable.ic_add_session),
            text = stringResource(com.zagirlek.home.R.string.create_session),
            modifier = Modifier
                .weight(1.5f)
                .fillMaxHeight()
                .clickable(onClick = onNewSessionClick)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DashboardMenuButton(
                iconPainter = painterResource(R.drawable.ic_computer),
                text = stringResource(com.zagirlek.home.R.string.computers),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clickable(onClick = onComputersListClick)
            )
            DashboardMenuButton(
                iconPainter = painterResource(R.drawable.ic_money),
                text = stringResource(com.zagirlek.home.R.string.tariffs),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clickable(onClick = onTariffsClick)
            )
        }
    }
}

@Composable
private fun DashboardMenuButton(
    iconPainter: Painter,
    text: String,
    modifier: Modifier = Modifier
) {
    AppCardFilled(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = iconPainter,
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DashboardMenuPreview() {
    NyTimesPreview {
        DashboardMenu()
    }
}