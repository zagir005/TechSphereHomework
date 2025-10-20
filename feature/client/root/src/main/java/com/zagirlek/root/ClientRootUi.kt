package com.zagirlek.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.zagirlek.favorite.FavoriteNewsScreenUi
import com.zagirlek.latest.LatestNewsScreenUi
import com.zagirlek.ui.R
import com.zagirlek.ui.elements.navigationbar.AppNavigationBar
import com.zagirlek.ui.elements.navigationbar.Tab
import com.zagirlek.weather.WeatherScreenUi
import kotlinx.coroutines.launch

internal data object Weather: Tab(
    nameResource = R.string.weather,
    iconResource = R.drawable.ic_weather
)
internal data object News: Tab(
    nameResource = R.string.news,
    iconResource = R.drawable.ic_news
)
internal data object Favorites: Tab(
    nameResource = R.string.favorites,
    iconResource = R.drawable.ic_favorite
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientRootUi(
    component: ClientRootComponent
) {
    val pages by component.pages.subscribeAsState()
    val tabItems = listOf(Weather, News, Favorites)
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
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
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ){ paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
        ){
            when(val child = pages.items[pages.selectedIndex].instance!!){
                is ClientRootComponent.Child.Weather -> {
                    WeatherScreenUi(component = child.component)
                }
                is ClientRootComponent.Child.News -> {
                    LatestNewsScreenUi(
                        component = child.component
                    ){
                        scope.launch {
                            snackbarHostState.showSnackbar(message = it)
                        }
                    }
                }
                is ClientRootComponent.Child.Favorites -> {
                    FavoriteNewsScreenUi(
                        component = child.component
                    )
                }
            }
        }
    }
}