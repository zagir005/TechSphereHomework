package com.zagirlek.addoredit.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.zagirlek.addoredit.model.toUser
import com.zagirlek.common.model.UserStatus
import com.zagirlek.common.utils.canceledJob
import com.zagirlek.common.validation.nickname.NicknameError
import com.zagirlek.common.validation.nickname.validateNickname
import com.zagirlek.common.validation.password.PasswordError
import com.zagirlek.common.validation.password.validatePassword
import com.zagirlek.common.validation.phone.PhoneError
import com.zagirlek.common.validation.phone.validatePhone
import com.zagirlek.user.model.User
import com.zagirlek.user.usecase.AddUserUseCase
import com.zagirlek.user.usecase.EditUserUseCase
import com.zagirlek.user.usecase.GetUserByIdUseCase
import com.zagirlek.user.usecase.IsNicknameUniqueUseCase
import com.zagirlek.user.usecase.IsPhoneUniqueUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddOrEditUserStoreFactory(
    private val userId: Long? = null,
    private val storeFactory: StoreFactory,
    private val addUserUseCase: AddUserUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val editUserUseCase: EditUserUseCase,
    private val isNicknameUniqueUseCase: IsNicknameUniqueUseCase,
    private val isPhoneUniqueUseCase: IsPhoneUniqueUseCase
) {
    fun create(): AddOrEditUserStore = object: AddOrEditUserStore, Store<AddOrEditUserStore.Intent, AddOrEditUserStore.State, AddOrEditUserStore.Label> by storeFactory.create(
        name = "add_or_edit_user_store",
        initialState = AddOrEditUserStore.State(),
        bootstrapper = SimpleBootstrapper(Action.LoadUser),
        executorFactory = ::ExecutorImpl,
        reducer = ReducerImpl
    ){ }
    var fieldJob by canceledJob()
    var currUser: User? = null
    private sealed interface Action {
        data object LoadUser: Action
        data object SaveUser: Action
    }
    private sealed class Msg{
        data class NicknameValue(val value: String): Msg()
        data class NicknameErrorValue(val value: NicknameError?): Msg()
        data class PhoneValue(val value: String): Msg()
        data class PhoneErrorValue(val value: PhoneError?): Msg()
        data class PasswordValue(val value: String): Msg()
        data class PasswordErrorValue(val value: PasswordError?): Msg()
        data class IsAdmin(val value: Boolean): Msg()
        data class CreateButtonAvailability(val value: Boolean): Msg()
    }
    private inner class ExecutorImpl: CoroutineExecutor<AddOrEditUserStore.Intent, Action, AddOrEditUserStore.State, Msg, AddOrEditUserStore.Label>() {
        override fun executeAction(action: Action) {
            when (action) {
                is Action.SaveUser -> {
                    scope.launch {
                        if (userId == null)
                            addUserUseCase(state().toUser())
                                .onSuccess {
                                    publish(AddOrEditUserStore.Label.OnFinish)
                                }
                        else
                            editUserUseCase(
                                user = state()
                                    .toUser()
                                    .copy(id = userId)
                            ).onSuccess {
                                publish(AddOrEditUserStore.Label.OnFinish)
                            }
                    }
                }
                Action.LoadUser -> scope.launch {
                    userId?.let {
                        currUser = getUserByIdUseCase(it)
                    }
                    currUser?.let {
                        dispatch(Msg.NicknameValue(it.nickname))
                        dispatch(Msg.PhoneValue(it.phone))
                        dispatch(Msg.PasswordValue(it.password))
                        dispatch(Msg.IsAdmin(it.status == UserStatus.ADMIN))
                        dispatch(Msg.CreateButtonAvailability(true))
                    }
                }
            }
        }

        override fun executeIntent(intent: AddOrEditUserStore.Intent) {
            when (intent) {
                is AddOrEditUserStore.Intent.NicknameEdit -> {
                    dispatch(Msg.NicknameValue(intent.value))
                    fieldJob = scope.launch(Dispatchers.IO) {
                        val error = nicknameFieldValidate(intent.value)
                        withContext(Dispatchers.Main){
                            dispatch(Msg.NicknameErrorValue(error))
                        }
                    }
                    dispatch(
                        Msg.CreateButtonAvailability(isCreateAvailable())
                    )
                }
                is AddOrEditUserStore.Intent.PhoneEdit -> {
                    dispatch(Msg.PhoneValue(intent.value))
                    fieldJob = scope.launch(Dispatchers.IO) {
                        val error = phoneFieldValidate(intent.value)
                        withContext(Dispatchers.Main){
                            dispatch(Msg.PhoneErrorValue(error))
                        }
                    }
                    dispatch(
                        Msg.CreateButtonAvailability(isCreateAvailable())
                    )
                }
                is AddOrEditUserStore.Intent.PasswordEdit -> {
                    dispatch(Msg.PasswordValue(intent.value))
                    dispatch(Msg.PasswordErrorValue(passwordFieldValidate(intent.value)))
                    dispatch(
                        Msg.CreateButtonAvailability(isCreateAvailable())
                    )
                }
                AddOrEditUserStore.Intent.Save -> forward(Action.SaveUser)
                AddOrEditUserStore.Intent.ToggleIsAdminStatus -> dispatch(Msg.IsAdmin(!state().isAdmin))
            }
        }

        private suspend fun nicknameFieldValidate(nickname: String): NicknameError? {
            return validateNickname(nickname) ?:
            if (!isNicknameUniqueUseCase(nickname))
                NicknameError.NicknameTextFieldError.Duplicate
            else null
        }

        private suspend fun phoneFieldValidate(phone: String): PhoneError? {
            return validatePhone(phone) ?:
            if (!isPhoneUniqueUseCase(phone))
                PhoneError.PhoneTextFieldError.Duplicate
            else null
        }

        private fun passwordFieldValidate(password: String): PasswordError? =
            validatePassword(password)

        private fun isCreateAvailable(): Boolean {
            return state().nicknameTextField.isNotEmpty() && state().passwordTextField.isNotEmpty() && state().phoneTextField.isNotEmpty()
                    && state().nicknameTextField.isValid() && state().passwordTextField.isValid() && state().phoneTextField.isValid()
        }
    }
    private object ReducerImpl: Reducer<AddOrEditUserStore.State, Msg> {
        override fun AddOrEditUserStore.State.reduce(msg: Msg): AddOrEditUserStore.State = when(msg){
            is Msg.NicknameValue -> copy(nicknameTextField = nicknameTextField.copy(value = msg.value))
            is Msg.NicknameErrorValue -> copy(nicknameTextField = nicknameTextField.copy(error = msg.value))
            is Msg.PhoneValue -> copy(phoneTextField = phoneTextField.copy(value = msg.value))
            is Msg.PhoneErrorValue -> copy(phoneTextField = phoneTextField.copy(error = msg.value))
            is Msg.PasswordValue -> copy(passwordTextField = passwordTextField.copy(value = msg.value))
            is Msg.PasswordErrorValue -> copy(passwordTextField = passwordTextField.copy(error = msg.value))
            is Msg.IsAdmin -> copy(isAdmin = msg.value)
            is Msg.CreateButtonAvailability -> copy(isCreateButtonEnabled = msg.value)
        }
    }
}