package com.zagirlek.nytimes.ui.screen.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.zagirlek.nytimes.ui.screen.splash.elements.SplashScreenContent


@Composable
fun SplashScreen(
    component: SplashComponent,
    modifier: Modifier = Modifier
) {
    val state by component.state.subscribeAsState()

    SplashScreenContent(
        state = state,
        action = component::action,
        modifier = modifier
    )
}

