package com.zagirlek.authhomework.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun UnderlineTextField(
    value: String,
    onValueChange: (String) -> Unit = {},
    label: String = "",
    errorMessage: String? = null,
    trailingIcon: @Composable () -> Unit = {},
    modifier: Modifier = Modifier,
) {

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        modifier = modifier,
        isError = errorMessage != null,
        supportingText = {
            Text(
                text = errorMessage.orEmpty(),
                color = Color.Red
            )
        },
        trailingIcon = trailingIcon,
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            errorTextColor = Color.White,
            errorLabelColor = TextFieldDefaults.colors().unfocusedLabelColor
        )
    )
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 4278190080,
    showSystemUi = true
)
@Composable
private fun UnderlineTextFieldPreview(modifier: Modifier = Modifier) {
    Box{
        UnderlineTextField(
            label = "Логин",
            modifier = Modifier.fillMaxWidth().padding(20.dp)
        )
    }

}