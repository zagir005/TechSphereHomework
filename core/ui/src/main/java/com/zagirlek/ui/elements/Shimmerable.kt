package com.zagirlek.ui.elements

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.valentinilk.shimmer.shimmer

@Composable
fun Modifier.shimmerable(enabled: Boolean): Modifier =
    if (!enabled)
        this
    else
        shimmer()