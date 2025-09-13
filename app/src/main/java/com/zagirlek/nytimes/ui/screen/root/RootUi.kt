package com.zagirlek.nytimes.ui.screen.root

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.zagirlek.nytimes.ui.screen.splash.SplashUi
import com.zagirlek.nytimes.ui.screen.login.LoginUi

@Composable
fun RootUi(
    rootComponent: RootComponent,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Children(
            stack = rootComponent.state,
            animation = stackAnimation(animator = fade() + scale()),
            modifier = Modifier.padding(paddingValues)
        ) {
            when (val child = it.instance) {
                is RootComponent.Child.SplashChild -> SplashUi(
                    child.splashChild
                )
                is RootComponent.Child.LoginChild -> LoginUi(
                    child.loginChild
                )
            }
        }
    }
}