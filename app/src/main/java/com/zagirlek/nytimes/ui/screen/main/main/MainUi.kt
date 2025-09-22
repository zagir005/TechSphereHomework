package com.zagirlek.nytimes.ui.screen.main.main

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.zagirlek.nytimes.R
import com.zagirlek.nytimes.ui.screen.main.weather.WeatherScreen
import com.zagirlek.nytimes.ui.theme.NyTimesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUi(
    component: MainComponent
) {
    val pages by component.pages.subscribeAsState()
    val tabItems = listOf(Tab.Weather, Tab.News, Tab.Favorites)

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
            MainNavigationBar(
                tabs = tabItems,
                selected = pages.selectedIndex,
            ){ selectedIndex ->
                component.selectPage(selectedIndex)
            }
        }
    ){ paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
        ){
            when(val child = pages.items[pages.selectedIndex].instance!!){
                is MainComponent.Child.Weather -> {
                    WeatherScreen(child.component)
                }
                is MainComponent.Child.News -> {

                }
                is MainComponent.Child.Favorites -> {

                }
            }
        }
    }
}

@Composable
private fun MainNavigationBar(
    tabs: List<Tab>,
    selected: Int,
    modifier: Modifier = Modifier,
    onItemClick: (index: Int) -> Unit,
) {
    val barItemShape = RoundedCornerShape(8.dp)

    NavigationBar(
        modifier
            .padding(4.dp)
            .height(75.dp),
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

private sealed class Tab(val nameResource: Int, val iconResource: Int){
    data object Weather: Tab(
        R.string.weather,
        R.drawable.ic_weather
    )
    data object News: Tab(
        R.string.news,
        R.drawable.ic_news

    )
    data object Favorites: Tab(
        R.string.favorites,
        R.drawable.ic_favorite
    )
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun MainBottomNavigationNightPreview() {
    val bottomBarItems = listOf(Tab.Weather, Tab.News, Tab.Favorites)
    NyTimesTheme{
        Box(
            modifier = Modifier.padding(30.dp).background(Color.Black)
        ) {
            MainNavigationBar(
                tabs = bottomBarItems,
                selected = 0,
            ){ selectedIndex ->

            }
        }
    }
}

@Preview
@Composable
private fun MainBottomNavigationDefaultPreview() {
    val bottomBarItems = listOf(Tab.Weather, Tab.News, Tab.Favorites)
    NyTimesTheme{
        Box(
            modifier = Modifier.padding(30.dp).background(Color.White)
        ) {
            MainNavigationBar(
                tabs = bottomBarItems,
                selected = 0,
            ){ selectedIndex ->

            }
        }
    }
}