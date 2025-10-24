package com.zagirlek.addoredit.cmp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.zagirlek.addoredit.AddOrEditUserScreen
import com.zagirlek.addoredit.model.AddOrEditUserModel
import com.zagirlek.addoredit.model.toModel
import com.zagirlek.addoredit.store.AddOrEditUserStore
import com.zagirlek.addoredit.store.AddOrEditUserStoreFactory
import com.zagirlek.common.utils.getStore
import com.zagirlek.user.usecase.AddUserUseCase
import com.zagirlek.user.usecase.EditUserUseCase
import com.zagirlek.user.usecase.GetUserByIdUseCase
import com.zagirlek.user.usecase.IsNicknameUniqueUseCase
import com.zagirlek.user.usecase.IsPhoneUniqueUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddOrEditUserScreenComponent(
    componentContext: ComponentContext,
    private val userId: Long? = null,
    private val storeFactory: StoreFactory,
    private val addUserUseCase: AddUserUseCase,
    private val isNicknameUniqueUseCase: IsNicknameUniqueUseCase,
    private val isPhoneUniqueUseCase: IsPhoneUniqueUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val editUserUseCase: EditUserUseCase,
    private val onFinish: () -> Unit,
): AddOrEditUserScreen, ComponentContext by componentContext {
    val componentScope = coroutineScope(context = SupervisorJob() + Dispatchers.Main.immediate).also {
        doOnDestroy { it.cancel() }
    }
    val storeInstance: AddOrEditUserStore = instanceKeeper.getStore {
        AddOrEditUserStoreFactory(
            userId = userId,
            storeFactory = storeFactory,
            addUserUseCase = addUserUseCase,
            isNicknameUniqueUseCase = isNicknameUniqueUseCase,
            isPhoneUniqueUseCase = isPhoneUniqueUseCase,
            getUserByIdUseCase = getUserByIdUseCase,
            editUserUseCase = editUserUseCase
        ).create()
    }
    override val model: StateFlow<AddOrEditUserModel> = storeInstance
        .stateFlow(lifecycle)
        .map {
            it.toModel()
        }
        .stateIn(
            scope = componentScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AddOrEditUserModel()
        )

    init {
        componentScope.launch {
            storeInstance.labels.collect {
                if (it is AddOrEditUserStore.Label.OnFinish)
                    onFinish()
            }
        }
    }

    override fun nicknameEdit(value: String) =
        storeInstance.accept(AddOrEditUserStore.Intent.NicknameEdit(value))

    override fun passwordEdit(value: String) =
        storeInstance.accept(AddOrEditUserStore.Intent.PasswordEdit(value))

    override fun phoneEdit(value: String) =
        storeInstance.accept(AddOrEditUserStore.Intent.PhoneEdit(value))

    override fun toggleAdminStatus() =
        storeInstance.accept(AddOrEditUserStore.Intent.ToggleIsAdminStatus)

    override fun saveUser() =
        storeInstance.accept(AddOrEditUserStore.Intent.Save)

    override fun cancel() =
        onFinish()


}