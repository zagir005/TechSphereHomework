package com.zagirlek.splash

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.zagirlek.splash.elements.SplashScreenContent


@Composable
fun SplashScreen(
    component: SplashComponent,
    version: String,
    modifier: Modifier = Modifier
) {
    val state by component.state.subscribeAsState()

    Scaffold { paddingValues ->
        SplashScreenContent(
            state = state,
            action = component::action,
            version = version,
            modifier = modifier
                .padding(paddingValues)
        )
    }
}

