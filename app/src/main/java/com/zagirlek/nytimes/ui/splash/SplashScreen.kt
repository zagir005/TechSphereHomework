package com.zagirlek.nytimes.ui.splash

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.zagirlek.nytimes.ui.splash.elements.SplashScreenContent


@Composable
fun SplashScreen(
    component: SplashComponent,
    modifier: Modifier = Modifier
) {
    val state by component.state.subscribeAsState()

    Scaffold { paddingValues ->
        SplashScreenContent(
            state = state,
            action = component::action,
            modifier = modifier
                .padding(paddingValues)
        )
    }
}

