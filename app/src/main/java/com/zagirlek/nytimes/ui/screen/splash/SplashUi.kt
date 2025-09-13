package com.zagirlek.nytimes.ui.screen.splash

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.zagirlek.nytimes.BuildConfig
import com.zagirlek.nytimes.R
import com.zagirlek.nytimes.ui.components.SpinningLoader
import com.zagirlek.nytimes.ui.screen.root.components.SplashComponent
import com.zagirlek.nytimes.ui.screen.splash.cmp.state.SplashAction
import com.zagirlek.nytimes.ui.screen.splash.cmp.state.SplashMutation
import com.zagirlek.nytimes.ui.theme.NyTimesTheme
import com.zagirlek.nytimes.ui.theme.robotoFlexFamily
import kotlinx.coroutines.delay


@Composable
fun SplashUi(
    splashComponent: SplashComponent,
    modifier: Modifier = Modifier
) {
    val state by splashComponent.state.subscribeAsState()

    var animVisibility by remember { mutableStateOf(false) }
    val animDuration = 200

    LaunchedEffect(state) {
        when (state) {
            SplashMutation.Loading -> animVisibility = true
            SplashMutation.OnFinish -> {
                animVisibility = false
                delay(animDuration.toLong())
                splashComponent.action(SplashAction.Finish)
            }
        }
    }


    Column (
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {

            SpinningLoader(
                modifier = Modifier.size(80.dp)
            )
            AnimatedVisibility(
                visible = animVisibility,
                enter = slideInVertically(
                    animationSpec = tween(
                        durationMillis = animDuration,
                        easing = FastOutSlowInEasing
                    ),
                    initialOffsetY = { it }
                ) + expandVertically(expandFrom = Alignment.Top) + fadeIn(initialAlpha = 0.3f)
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    fontSize = 40.sp,
                    fontFamily = robotoFlexFamily
                )
            }
        }

        AnimatedVisibility(
            visible = animVisibility,
            enter = slideInVertically(
                animationSpec = tween(
                    durationMillis = animDuration,
                    easing = FastOutSlowInEasing
                ),
                initialOffsetY = { it }
            ) + expandVertically(expandFrom = Alignment.Top) + fadeIn(initialAlpha = 0.3f),
        ) {
            Text(
                text = BuildConfig.VERSION_NAME,
                fontSize = 14.sp,
                fontFamily = robotoFlexFamily
            )
        }
    }
}




@Preview(
    name = "Default",
    showSystemUi = true
)
@Composable
private fun SplashUiDefaultPreview() {
    val previewComponent = object: SplashComponent {
        override val state: Value<SplashMutation> = MutableValue(SplashMutation.Loading)

        override fun action(splashAction: SplashAction) {
        }
    }

    NyTimesTheme {
        Scaffold{ paddingValues ->
            SplashUi(
                splashComponent = previewComponent,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            )
        }
    }
}


@Preview(
    name = "Night",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun SplashUiNightPreview() {
    val previewComponent = object: SplashComponent {
        override val state: Value<SplashMutation> = MutableValue(SplashMutation.Loading)

        override fun action(splashAction: SplashAction) {
        }
    }

    NyTimesTheme {
        Scaffold{ paddingValues ->
            SplashUi(
                splashComponent = previewComponent,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            )
        }
    }
}

