package com.zagirlek.add.cmp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.zagirlek.add.AddUserScreen
import com.zagirlek.add.model.AddUserModel
import com.zagirlek.add.model.toModel
import com.zagirlek.add.store.AddUserStore
import com.zagirlek.add.store.AddUserStoreFactory
import com.zagirlek.common.utils.getStore
import com.zagirlek.user.usecase.AddUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AddUserScreenComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val addUserUseCase: AddUserUseCase
): AddUserScreen, ComponentContext by componentContext {
    val componentScope = coroutineScope(context = SupervisorJob() + Dispatchers.Main.immediate).also {
        doOnDestroy { it.cancel() }
    }
    val storeInstance: AddUserStore = instanceKeeper.getStore {
        AddUserStoreFactory(
            storeFactory = storeFactory,
            addUserUseCase = addUserUseCase
        ).create()
    }
    override val model: StateFlow<AddUserModel> = storeInstance
        .stateFlow(lifecycle)
        .map {
            it.toModel()
        }
        .stateIn(
            scope = componentScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AddUserModel()
        )

    override fun nicknameEdit(value: String) =
        storeInstance.accept(AddUserStore.Intent.NicknameEdit(value))

    override fun passwordEdit(value: String) =
        storeInstance.accept(AddUserStore.Intent.PasswordEdit(value))

    override fun phoneEdit(value: String) =
        storeInstance.accept(AddUserStore.Intent.PhoneEdit(value))

    override fun toggleAdminStatus() =
        storeInstance.accept(AddUserStore.Intent.ToggleIsAdminStatus)

    override fun saveUser() {
        storeInstance.accept(AddUserStore.Intent.Save)

    }
}