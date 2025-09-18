package com.zagirlek.nytimes.ui.screen.main.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
fun MainUi(
    mainComponent: MainComponent
) {
    val pages by mainComponent.pages.subscribeAsState()
    Scaffold(
        bottomBar = {
            NavigationBar {
                pages.items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = pages.selectedIndex == index,
                        onClick = { mainComponent.selectTab(index) },
                        icon = {
                            item.instance?.iconResource?.let {
                                Icon(
                                    painterResource(it),
                                    stringResource(item.instance?.nameResource!!)
                                )
                            }
                        }
                    )
                }
            }
        }
    ){ paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
        ){
            when(val child = pages.items[pages.selectedIndex].instance!!){
                is MainComponent.Child.Weather -> {
                    
                }
                is MainComponent.Child.News -> {

                }
                is MainComponent.Child.Favorites -> {

                }
            }
        }
    }
}