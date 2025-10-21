package com.zagirlek.list.elements

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zagirlek.ui.elements.NyTimesPreview
import com.zagirlek.user.model.User

@Composable
internal fun UserList(
    list: List<User>,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit = {},
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = list,
            key = { it.id }
        ) {
            UserItemCard(
                user = it,
                onClick = onItemClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem()
            )
        }
    }
}

@Preview
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun UserListPreview() {
    NyTimesPreview {
        UserList(
            List(10){ User.exampleUser() },
            modifier = Modifier.fillMaxSize()
        )
    }
}