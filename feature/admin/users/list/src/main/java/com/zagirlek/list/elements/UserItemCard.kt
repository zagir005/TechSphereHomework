package com.zagirlek.list.elements

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zagirlek.common.model.User
import com.zagirlek.ui.elements.AppCardFilled
import com.zagirlek.ui.theme.NyTimesTheme

@Composable
internal fun UserItemCard(
    user: User,
    modifier: Modifier = Modifier,
    onEditClick: (User) -> Unit = {},
    onDeleteClick: (User) -> Unit = {}
) {
    AppCardFilled(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            UserInfoColumn(
                nickname = user.nickname,
                phone = user.phone,
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = { onEditClick(user) }
                ) {
                    Icon(
                        Icons.Default.Edit,
                        null
                    )
                }
                VerticalDivider(
                    modifier = Modifier
                        .height(32.dp),
                    color = MaterialTheme.colorScheme.outlineVariant,
                    thickness = 1.dp
                )
                IconButton(
                    onClick = { onDeleteClick(user) }
                ) {
                    Icon(
                        Icons.Default.DeleteOutline,
                        null
                    )
                }
            }
        }
    }
}

@Preview
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun UserCardPreview() {
    NyTimesTheme {
        UserItemCard (
            user = User.exampleUser()
        )
    }
}