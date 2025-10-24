package com.zagirlek.ui.elements.navigationbar

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun AppNavigationBar(
    tabs: List<Tab>,
    selected: Int,
    modifier: Modifier = Modifier,
    onItemClick: (index: Int) -> Unit,
) {
    val barItemShape = RoundedCornerShape(8.dp)

    NavigationBar(
        modifier
            .padding(4.dp),
        containerColor = Color.Transparent
    ) {
        tabs.forEachIndexed { index, tab ->
            val backgroundColor by animateColorAsState(
                targetValue = if (selected == index) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                else Color.Transparent,
                label = "backgroundAnim"
            )

            NavigationBarItem(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.onSurface,
                        shape = barItemShape
                    )
                    .background(
                        color = backgroundColor,
                        shape = barItemShape
                    ),
                selected = selected == index,
                onClick = { onItemClick(index) },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                    selectedIconColor = MaterialTheme.colorScheme.onSurface,
                    selectedTextColor = MaterialTheme.colorScheme.onSurface
                ),
                icon = {
                    Icon(
                        painter = painterResource(tab.iconResource),
                        contentDescription = stringResource(tab.nameResource)
                    )
                },
                label = {
                    Text(
                        text = stringResource(tab.nameResource)
                    )
                }
            )
        }
    }
}

open class Tab(val nameResource: Int, val iconResource: Int)