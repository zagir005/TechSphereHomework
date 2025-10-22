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
import com.zagirlek.list.store.UserStore
import com.zagirlek.list.store.UserStoreFactory
import com.zagirlek.user.usecase.DeleteUserByIdUseCase
import com.zagirlek.user.usecase.GetUserListFlowUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class UserListScreenComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val getUserListFlowUseCase: GetUserListFlowUseCase,
    private val deleteUserByIdUseCase: DeleteUserByIdUseCase,
    private val addOrEditUserModule: AddOrEditUserFeatureModule,
): UserListScreen, ComponentContext by componentContext{
    private val componentScope = coroutineScope(
        context = SupervisorJob() + Dispatchers.Main.immediate
    ).also {
        doOnDestroy { it.cancel() }
    }

    private val storeInstance = instanceKeeper.getStore {
        UserStoreFactory(
            storeFactory = storeFactory,
            userListFlow = getUserListFlowUseCase
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
        addOrEditUserModule.getAddUserComponent(
            componentContext = childComponentContext,
            onFinish = {
                hideAddEditUserDialog()
            },
            userId = config.userId
        )
    }

    override fun search(query: String) {
        storeInstance.accept(UserStore.Intent.SearchFieldChange(query.ifEmpty { null }))
    }
    override fun deleteUser(userId: Long) {
        componentScope.launch {
            deleteUserByIdUseCase(userId)
        }
    }

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