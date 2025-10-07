package com.zagirlek.nytimes.ui.screen.splash.elements

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zagirlek.nytimes.BuildConfig
import com.zagirlek.nytimes.R
import com.zagirlek.nytimes.ui.screen.splash.cmp.state.SplashAction
import com.zagirlek.nytimes.ui.screen.splash.cmp.state.SplashState
import com.zagirlek.nytimes.core.ui.theme.NyTimesTheme
import com.zagirlek.nytimes.ui.theme.robotoFlexFamily
import kotlinx.coroutines.delay

@Composable
fun SplashScreenContent(
    state: SplashState,
    action: (SplashAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val animDuration = 180
    var animVisibility by remember { mutableStateOf(false) }

    LaunchedEffect(state) {
        animVisibility = state.isLoading
        delay(animDuration.toLong())
        if(!state.isLoading){
            action(SplashAction.SplashFinished)
        }
    }

    Column (
        modifier = modifier
            .fillMaxWidth(),
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
    showSystemUi = true
)
@Composable
private fun SplashUiDefaultPreview() {
    NyTimesTheme {
        Scaffold{ paddingValues ->
            SplashScreenContent(
                state = SplashState(isLoading = true),
                action = { },
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}


@Preview(
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun SplashUiNightPreview() {
    NyTimesTheme {
        Scaffold{ paddingValues ->
            SplashScreenContent(
                state = SplashState(isLoading = true),
                action = { },
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}