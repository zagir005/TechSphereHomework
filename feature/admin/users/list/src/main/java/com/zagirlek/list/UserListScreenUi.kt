package com.zagirlek.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.zagirlek.list.elements.UserListScreenContent

@Composable
fun UserListScreenUi(
    component: UserListScreen
) {
    val model by component.model.collectAsState()

    UserListScreenContent(
        model = model,
        onSearchFieldChange = { component.search(it) }
    )
}