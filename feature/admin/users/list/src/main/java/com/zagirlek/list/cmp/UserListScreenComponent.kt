package com.zagirlek.list.cmp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.zagirlek.addoredit.AddOrEditUserScreen
import com.zagirlek.addoredit.di.AddOrEditUserFeatureModule
import com.zagirlek.common.utils.getStore
import com.zagirlek.list.UserListScreen
import com.zagirlek.list.model.UserListModel
import com.zagirlek.list.model.toModel
import com.zagirlek.list.store.UserListStore
import com.zagirlek.list.store.UserListStoreFactory
import com.zagirlek.user.usecase.DeleteUserByIdUseCase
import com.zagirlek.user.usecase.GetUsersWithCurrentUserFlowUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

internal class UserListScreenComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val deleteUserByIdUseCase: DeleteUserByIdUseCase,
    private val addOrEditUserModule: AddOrEditUserFeatureModule,
    private val getUsersWithCurrentUserFlowUseCase: GetUsersWithCurrentUserFlowUseCase,
    private val onLogout: () -> Unit,
): UserListScreen, ComponentContext by componentContext{
    private val componentScope = coroutineScope(
        context = SupervisorJob() + Dispatchers.Main.immediate
    ).also {
        doOnDestroy { it.cancel() }
    }
    private val storeInstance = instanceKeeper.getStore {
        UserListStoreFactory(
            storeFactory = storeFactory,
            deleteUserByIdUseCase = deleteUserByIdUseCase,
            getUsersWithCurrentUserFlowUseCase = getUsersWithCurrentUserFlowUseCase
        ).create()
    }
    override val model: StateFlow<UserListModel> = storeInstance
        .stateFlow(lifecycle)
        .map {
            it.toModel()
        }
        .stateIn(
            scope = componentScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserListModel()
        )
    private val dialogNavigation = SlotNavigation<AddOrEditUserScreen.AddOrEditUserConfig>()
    override val dialog: Value<ChildSlot<*, AddOrEditUserScreen>> = childSlot(
        source = dialogNavigation,
        serializer = AddOrEditUserScreen.AddOrEditUserConfig.serializer(),
        handleBackButton = true,
    ) { config, childComponentContext ->
        addOrEditUserModule.getAddOrEditUserComponent(
            componentContext = childComponentContext,
            onFinish = {
                hideAddEditUserDialog()
            },
            userId = config.userId
        )
    }

    override fun search(query: String) {
        storeInstance.accept(UserListStore.Intent.SearchFieldChange(query.ifEmpty { null }))
    }
    override fun deleteUser(userId: Long) {
        storeInstance.accept(UserListStore.Intent.DeleteUser(userId))
    }
    override fun onDialogDismiss() {
        storeInstance.accept(UserListStore.Intent.DialogDismiss)
    }

    override fun logout() = onLogout()

    override fun addUser() {
        dialogNavigation.activate(
            AddOrEditUserScreen.AddOrEditUserConfig(null)
        )
    }
    override fun editUser(userId: Long) {
        dialogNavigation.activate(
            AddOrEditUserScreen.AddOrEditUserConfig(userId)
        )
    }
    override fun hideAddEditUserDialog() {
        dialogNavigation.dismiss()
    }
}