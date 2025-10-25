package com.zagirlek.list.elements

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zagirlek.common.model.User
import com.zagirlek.common.model.UserType
import com.zagirlek.common.utils.ifNotEmpty
import com.zagirlek.list.R
import com.zagirlek.ui.elements.NyTimesPreview
import com.zagirlek.ui.elements.TitleText

@Composable
internal fun UserList(
    userList: List<User>,
    modifier: Modifier = Modifier,
    onEditClick: (User) -> Unit = {},
    onDeleteClick: (User) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        userList.filter { it.status == UserType.ADMIN }.ifNotEmpty { list ->
            item {
                TitleText(
                    text = stringResource(R.string.admins)
                )
            }
            items(
                items = list,
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
        userList.filter { it.status == UserType.CLIENT }.ifNotEmpty { list ->
            item {
                TitleText(
                    text = stringResource(R.string.clients)
                )
            }
            items(
                items = list,
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