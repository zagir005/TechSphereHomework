package com.zagirlek.list.elements

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zagirlek.ui.elements.AppCardFilled
import com.zagirlek.ui.theme.NyTimesTheme
import com.zagirlek.user.model.User

@Composable
internal fun UserItemCard(
    user: User,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    AppCardFilled(
        modifier = modifier,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = user.nickname,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = user.phone,
                style = MaterialTheme.typography.titleSmall
            )
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