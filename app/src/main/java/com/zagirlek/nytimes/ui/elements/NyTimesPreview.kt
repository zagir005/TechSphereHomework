package com.zagirlek.nytimes.ui.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zagirlek.nytimes.ui.theme.NyTimesTheme

@Composable
fun NyTimesPreview(content: @Composable () -> Unit) {
    NyTimesTheme {
        Scaffold { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                content()
            }
        }
    }
}