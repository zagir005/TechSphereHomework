package com.zagirlek.list.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zagirlek.common.model.User
import com.zagirlek.common.model.UserType
import com.zagirlek.list.model.UserListModel
import com.zagirlek.ui.R
import com.zagirlek.ui.elements.TitleText
import com.zagirlek.ui.elements.shimmerable
import com.zagirlek.ui.elements.textfield.AppTextField

@Composable
internal fun UserListScreenContent(
    model: UserListModel,
    onSearchFieldChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onEditClick: (User) -> Unit = {},
    onDeleteClick: (User) -> Unit = {},
    onLogoutClick: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        AppTextField(
            value = model.searchField ?: "",
            onValueChange = { onSearchFieldChange(it) },
            label = stringResource(R.string.search),
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            singleLine = true
        )
        TitleText(
            text = stringResource(com.zagirlek.list.R.string.you)
        )
        CurrentUserCard(
            user = model.currentUser ?: User.exampleUser(),
            modifier = Modifier
                .padding(4.dp)
                .shimmerable(model.currentUser == null),
            onEditClick = onEditClick,
            onLogoutClick = onLogoutClick
        )
        UserList(
            userList = model.userList ?: List(4) {
                User(
                    status = UserType.entries.random(),
                    id = it.toLong()
                )
            },
            onEditClick = onEditClick,
            onDeleteClick = onDeleteClick,
            modifier = Modifier
                .shimmerable(model.userList == null)
        )
    }
}