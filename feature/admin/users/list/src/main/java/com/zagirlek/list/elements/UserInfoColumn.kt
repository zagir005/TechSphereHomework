package com.zagirlek.list.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun UserInfoColumn(
    nickname: String,
    phone: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier

    ) {
        Text(
            text = nickname,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = phone,
            style = MaterialTheme.typography.titleSmall
        )
    }

}