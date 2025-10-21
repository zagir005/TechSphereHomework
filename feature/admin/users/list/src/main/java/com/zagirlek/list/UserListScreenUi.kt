package com.zagirlek.list

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.zagirlek.add.AddUserScreenUi
import com.zagirlek.list.elements.UserListScreenContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreenUi(
    component: UserListScreen
) {
    val model by component.model.collectAsState()
    val childSlot by component.dialog.subscribeAsState()

    childSlot.child?.instance?.let { sheetComponent ->
        ModalBottomSheet(
            onDismissRequest = { component.hideAddUser() },
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ) {
            AddUserScreenUi(sheetComponent)
        }
    }

    UserListScreenContent(
        model = model,
        onSearchFieldChange = { component.search(it); component.addUser() }
    )
}