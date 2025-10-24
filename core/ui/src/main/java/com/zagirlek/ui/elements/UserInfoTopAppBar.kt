package com.zagirlek.ui.elements

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zagirlek.ui.R

@Composable
fun UserInfoTopAppBar(
    nickname: String,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(width = 1.dp, Color.Black)
            .padding(4.dp)
    ){
        Row {
            Text(text = nickname)
            Spacer(
                modifier = Modifier
                    .weight(1f)
            )
            IconButton(
                onClick = onLogout
            ) {
                Icon(
                    Icons.AutoMirrored.Default.Logout,
                    stringResource(R.string.logout)
                )
            }
        }
    }
}