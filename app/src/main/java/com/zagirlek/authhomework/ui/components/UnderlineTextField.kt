package com.zagirlek.authhomework.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun UnderlineTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit = {},
    label: String = "",
    errorMessage: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable () -> Unit = {},
) {

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        modifier = modifier,
        isError = errorMessage != null,
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

            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            errorTextColor = Color.White,

            errorLabelColor = Color.DarkGray,
            focusedLabelColor = Color.LightGray,
            disabledLabelColor = Color.DarkGray,
            unfocusedLabelColor = Color.LightGray,

            errorTrailingIconColor = Color.White,
            focusedTrailingIconColor = Color.White,
            disabledTrailingIconColor = Color.White,
            unfocusedTrailingIconColor = Color.White,

            errorIndicatorColor = Color.Red,
            unfocusedIndicatorColor = Color.DarkGray,
            focusedIndicatorColor = Color.LightGray,
        )
    )
}



@Preview(
    name = "Empty",
    showBackground = true,
    backgroundColor = 0x000000.toLong()

)
@Composable
fun EmptyUnderlineTextFieldPreview(modifier: Modifier = Modifier) {
    UnderlineTextField(
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

@Preview(
    name = "Example text",
    showBackground = true,
    backgroundColor = 0x000000.toLong()
)
@Composable
fun ExampleTextUnderlineTextFieldPreview(modifier: Modifier = Modifier) {
    UnderlineTextField(
        modifier = Modifier,
        "Example text",
        label = "Write here",
        trailingIcon = {
            Icon(
                Icons.Default.Create,
                null
            )
        }
    )
}

@Preview(
    name = "With Error",
    showBackground = true,
    backgroundColor = 0x000000.toLong()
)
@Composable
fun UnderlineTextFieldWithErrorPreview(modifier: Modifier = Modifier) {
    UnderlineTextField(
        modifier = modifier,
        "Example text",
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