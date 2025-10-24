package com.zagirlek.ui.elements.alertdialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun AppAlertDialog(
    state: AlertDialogState?,
    onDismiss: () -> Unit
) {
    if (state == null) return

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(state.title) },
        text = { Text(state.message) },
        confirmButton = {
            state.confirmButton?.let { btn ->
                TextButton(onClick = {
                    btn.onClick()
                    onDismiss()
                }) {
                    Text(btn.text)
                }
            }
        },
        dismissButton = {
            state.cancelButton?.let { btn ->
                TextButton(onClick = {
                    btn.onClick()
                    onDismiss()
                }) {
                    Text(btn.text)
                }
            }
        }
    )
}
