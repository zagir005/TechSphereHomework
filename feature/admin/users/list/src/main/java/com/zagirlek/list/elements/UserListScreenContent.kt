package com.zagirlek.list.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.zagirlek.common.model.UserStatus
import com.zagirlek.list.model.UserListModel
import com.zagirlek.ui.R
import com.zagirlek.ui.elements.shimmerable
import com.zagirlek.ui.elements.textfield.AppTextField
import com.zagirlek.user.model.User

@Composable
internal fun UserListScreenContent(
    model: UserListModel,
    onSearchFieldChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onEditClick: (User) -> Unit = {},
    onDeleteClick: (User) -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // TODO: Возможно стоит сделать отдельный AppSearchField
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
        UserList(
            list = model.userList ?: List(4) {
                User(
                    status = UserStatus.entries.random(),
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