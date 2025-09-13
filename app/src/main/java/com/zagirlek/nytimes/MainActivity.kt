package com.zagirlek.nytimes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.zagirlek.nytimes.ui.screen.root.RootComponent
import com.zagirlek.nytimes.ui.screen.root.RootUi
import com.zagirlek.nytimes.ui.theme.NyTimesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val rootComponent = RootComponent(defaultComponentContext())

        setContent {
            NyTimesTheme {
                RootUi(rootComponent)
            }
        }
    }
}