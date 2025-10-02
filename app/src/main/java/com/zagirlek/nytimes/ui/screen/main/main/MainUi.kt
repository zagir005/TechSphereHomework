package com.zagirlek.nytimes.ui.screen.main.main

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
import com.zagirlek.nytimes.ui.screen.main.main.element.MainNavigationBar
import com.zagirlek.nytimes.ui.screen.main.main.element.Tab
import com.zagirlek.nytimes.ui.screen.main.news.NewsScreenUi
import com.zagirlek.nytimes.ui.screen.main.weather.WeatherScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUi(
    component: MainComponent
) {
    val pages by component.pages.subscribeAsState()
    val tabItems = listOf(Tab.Weather, Tab.News, Tab.Favorites)
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
            MainNavigationBar(
                tabs = tabItems,
                selected = pages.selectedIndex,
                modifier = Modifier
                    .height(navigationBarHeight)
            ){ selectedIndex ->
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
                is MainComponent.Child.Weather -> {
                    WeatherScreen(
                        component = child.component)
                }
                is MainComponent.Child.News -> {
                    NewsScreenUi(
                        component = child.component
                    ){
                        scope.launch {
                            snackbarHostState.showSnackbar(message = it)
                        }
                    }
                }
                is MainComponent.Child.Favorites -> {

                }
            }
        }
    }
}