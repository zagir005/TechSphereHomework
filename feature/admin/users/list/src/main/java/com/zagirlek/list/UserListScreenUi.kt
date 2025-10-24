package com.zagirlek.list

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.zagirlek.addoredit.AddOrEditUserScreenUi
import com.zagirlek.list.elements.UserListScreenContent
import com.zagirlek.list.elements.UserListTopAppBar
import com.zagirlek.ui.elements.alertdialog.AppAlertDialog
import com.zagirlek.ui.elements.navigationbar.Tab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreenUi(
    component: UserListScreen,
    topBar: ( @Composable (tabInfo: Tab) -> Unit ) -> Unit
) {
    val model by component.model.collectAsState()
    val childSlot by component.dialog.subscribeAsState()

    childSlot.child?.instance?.let { sheetComponent ->
        ModalBottomSheet(
            onDismissRequest = { component.hideAddEditUserDialog() },
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            AddOrEditUserScreenUi(sheetComponent)
        }
    }

    topBar { tabInfo ->
        UserListTopAppBar(tabInfo) { component.addUser() }
    }

    AppAlertDialog(
        state = model.alertDialogState,
        onDismiss = { component.onDialogDismiss() }
    )

    UserListScreenContent(
        model = model,
        onSearchFieldChange = { component.search(it) },
        onEditClick = { component.editUser(it.id) },
        onDeleteClick = { component.deleteUser(it.id) },
        onLogoutClick = { component.logout() }
    )
}