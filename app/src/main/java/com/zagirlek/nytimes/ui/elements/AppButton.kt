package com.zagirlek.nytimes.ui.elements

import android.content.res.Configuration
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zagirlek.nytimes.ui.theme.NyTimesTheme

@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(4.dp),
    content: @Composable () -> Unit,
) {
    Button(
        onClick = onClick,
        shape = shape,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor =
                MaterialTheme.colorScheme.onSurface
                    .copy(alpha = 0.5f),
            disabledContentColor =
                MaterialTheme.colorScheme.onPrimary
                    .copy(alpha = 0.55f)
        ),
        modifier = modifier
    ) {
        content()
    }
}


@Preview(
    name = "Default"
)
@Composable
private fun AppButtonDefaultPreview() {
    NyTimesTheme {
        AppButton(
            onClick = {

            },
            enabled = false
        ) {
            Text("Button")
        }
    }
}
@Preview(
    name = "Night",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun AppButtonNightPreview() {
    NyTimesTheme {
        AppButton(
            onClick = {

            },
            enabled = false
        ) {
            Text("Button")
        }
    }
}