package com.zagirlek.list.elements

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zagirlek.common.model.UserType
import com.zagirlek.list.R
import com.zagirlek.ui.elements.NyTimesPreview
import com.zagirlek.user.model.User

@Composable
internal fun UserList(
    list: List<User>,
    modifier: Modifier = Modifier,
    onEditClick: (User) -> Unit = {},
    onDeleteClick: (User) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        item {
            Text(
                text = stringResource(R.string.admins),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Light),
                modifier = Modifier
                    .padding(4.dp),
            )
        }
        items(
            items = list.filter { it.status == UserType.ADMIN },
            key = { it.id }
        ) {
            UserItemCard(
                user = it,
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem()
            )
        }
        item {
            Text(
                text = stringResource(R.string.clients),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Light),
                modifier = Modifier
                    .padding(4.dp)
            )
        }
        items(
            items = list.filter { it.status == UserType.CLIENT },
            key = { it.id }
        ) {
            UserItemCard(
                user = it,
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick,
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