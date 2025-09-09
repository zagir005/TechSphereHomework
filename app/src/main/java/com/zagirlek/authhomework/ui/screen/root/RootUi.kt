package com.zagirlek.authhomework.ui.screen.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.zagirlek.authhomework.ui.screen.loading.SplashUi
import com.zagirlek.authhomework.ui.screen.login.LoginUi

@Composable
fun RootUi(
    rootComponent: RootComponent,
    modifier: Modifier = Modifier
) {
    Children(
        stack = rootComponent.state,
        animation = stackAnimation(animator = fade() + scale()),
        modifier = modifier
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