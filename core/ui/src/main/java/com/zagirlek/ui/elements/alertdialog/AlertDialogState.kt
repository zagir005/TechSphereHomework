package com.zagirlek.ui.elements.alertdialog

data class AlertDialogState(
    val title: String,
    val message: String,
    val confirmButton: DialogButton? = null,
    val cancelButton: DialogButton? = null
)

data class DialogButton(
    val text: String,
    val onClick: () -> Unit
)

