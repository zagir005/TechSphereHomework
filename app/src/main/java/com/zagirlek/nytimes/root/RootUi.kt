package com.zagirlek.nytimes.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.zagirlek.auth.AuthScreenUi
import com.zagirlek.nytimes.BuildConfig
import com.zagirlek.root.AdminRootUi
import com.zagirlek.root.ClientRootUi
import com.zagirlek.splash.SplashScreen

@Composable
fun RootUi(rootComponent: RootComponent) {
    Children(
        stack = rootComponent.stack
    ) {
        when(val child = it.instance){
            is RootComponent.Child.Splash -> SplashScreen(component = child.component, version = BuildConfig.VERSION_NAME)
            is RootComponent.Child.Login -> AuthScreenUi(component = child.component)
            is RootComponent.Child.ClientRoot -> ClientRootUi(component = child.component)
            is RootComponent.Child.AdminRoot -> AdminRootUi(component = child.component)
        }
    }
}