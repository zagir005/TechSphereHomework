package com.zagirlek.nytimes.ui.screen.auth.elements.authdialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.zagirlek.nytimes.R

@Composable
fun AuthErrorDialog (
    state: AuthErrorDialogState,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = { onDismiss() }
            ){
                Text(text = stringResource(R.string.ok))
            }
        },
        title = { Text(text = state.title) },
        text = { Text(text = state.description) },
        modifier = modifier
    )
}