package com.zagirlek.nytimes.ui.elements

import android.content.res.Configuration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zagirlek.nytimes.ui.theme.NyTimesTheme

@Composable
fun AppTextField(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
    label: String = "",
    errorMessage: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable () -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    textStyle: TextStyle = LocalTextStyle.current,
    singleLine: Boolean = false,
    interactionSource: MutableInteractionSource? = null,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = singleLine,
        modifier = modifier,
        isError = errorMessage != null,
        keyboardOptions = keyboardOptions,
        interactionSource = interactionSource,
        textStyle = textStyle,
        supportingText = {
            if(errorMessage != null){
                Text(
                    text = errorMessage,
                    color = Color.Red
                )
            }
        },
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,

            focusedLabelColor = MaterialTheme.colorScheme.onSurface
                .copy(alpha = 0.5f),
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface
                .copy(alpha = 0.5f),
            errorLabelColor = MaterialTheme.colorScheme.onSurface
                .copy(alpha = 0.5f),

            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
            errorTrailingIconColor = MaterialTheme.colorScheme.onSurface,
            focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,

            focusedIndicatorColor = MaterialTheme.colorScheme.onSurface
                .copy(alpha = 0.5f),
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface
                .copy(alpha = 0.5f),
            errorIndicatorColor = Color.Red
        )
    )
}



@Preview(
    name = "Empty, Default",
    showBackground = true
)
@Composable
private fun EmptyUnderlineTextFieldPreview() {
    NyTimesTheme {
        Surface(Modifier.padding(20.dp)){
            AppTextField(
                value = "",
                label = "Write here",
                trailingIcon = {
                    Icon(
                        Icons.Default.Create,
                        null
                    )
                }
            )
        }
    }
}

@Preview(
    name = "Example text, Default",
    showBackground = true
)
@Composable
private fun ExampleTextUnderlineTextFieldPreview() {
    NyTimesTheme {
        Surface(Modifier.padding(20.dp)) {
            AppTextField(
                "Example text",
                modifier = Modifier,
                label = "Write here",
                trailingIcon = {
                    Icon(
                        Icons.Default.Create,
                        null
                    )
                }
            )
        }
    }
}

@Preview(
    name = "With Error, Default",
    showBackground = true
)
@Composable
private fun UnderlineTextFieldWithErrorPreview() {
    NyTimesTheme {
        Surface(Modifier.padding(20.dp)) {
            AppTextField(
                value = "Example text",
                errorMessage = "Error!",
                label = "Write here",
                trailingIcon = {
                    Icon(
                        Icons.Default.Create,
                        null
                    )
                }
            )
        }
    }
}


@Preview(
    name = "Empty, Night",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun EmptyTextFieldNightPreview() {
    NyTimesTheme {
        Surface(Modifier.padding(20.dp)) {
            AppTextField(
                value = "",
                label = "Write here",
                trailingIcon = {
                    Icon(
                        Icons.Default.Create,
                        null
                    )
                }
            )
        }
    }
}

@Preview(
    name = "Example text, Night",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun ExampleTextFieldNightPreview() {
    NyTimesTheme {
        Surface(Modifier.padding(20.dp)) {
            AppTextField(
                "Example text",
                modifier = Modifier,
                label = "Write here",
                trailingIcon = {
                    Icon(
                        Icons.Default.Create,
                        null
                    )
                }
            )
        }
    }
}

@Preview(
    name = "With Error, Night",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun WithErrorFieldNightPreview() {
    NyTimesTheme {
        Surface(Modifier.padding(20.dp)) {
            AppTextField(
                value = "Example text",
                errorMessage = "Error!",
                label = "Write here",
                trailingIcon = {
                    Icon(
                        Icons.Default.Create,
                        null
                    )
                }
            )
        }
    }
}