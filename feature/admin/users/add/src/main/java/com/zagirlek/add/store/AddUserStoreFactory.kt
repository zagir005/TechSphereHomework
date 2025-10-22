package com.zagirlek.add.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.zagirlek.add.model.toUser
import com.zagirlek.add.store.AddUserStore.Intent
import com.zagirlek.add.store.AddUserStore.Label
import com.zagirlek.add.store.AddUserStore.State
import com.zagirlek.common.utils.canceledJob
import com.zagirlek.common.validation.nickname.NicknameError
import com.zagirlek.common.validation.nickname.validateNickname
import com.zagirlek.common.validation.password.PasswordError
import com.zagirlek.common.validation.password.validatePassword
import com.zagirlek.common.validation.phone.PhoneError
import com.zagirlek.common.validation.phone.validatePhone
import com.zagirlek.user.usecase.AddUserUseCase
import com.zagirlek.user.usecase.IsNicknameUniqueUseCase
import com.zagirlek.user.usecase.IsPhoneUniqueUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddUserStoreFactory(
    private val storeFactory: StoreFactory,
    private val addUserUseCase: AddUserUseCase,
    private val isNicknameUniqueUseCase: IsNicknameUniqueUseCase,
    private val isPhoneUniqueUseCase: IsPhoneUniqueUseCase
) {
    fun create(): AddUserStore = object: AddUserStore, Store<Intent, State, Label> by storeFactory.create(
        name = "add_or_edit_user_store",
        initialState = State(),
        executorFactory = ::ExecutorImpl,
        reducer = ReducerImpl
    ){ }
    var fieldJob by canceledJob()
    private sealed interface Action {
        data object SaveUser: Action
    }
    private sealed class Msg{
        data class NicknameValue(val value: String): Msg()
        data class NicknameErrorValue(val value: NicknameError?): Msg()
        data class PhoneValue(val value: String): Msg()
        data class PhoneErrorValue(val value: PhoneError?): Msg()
        data class Password(val value: String): Msg()
        data class PasswordErrorValue(val value: PasswordError?): Msg()
        data class IsAdmin(val value: Boolean): Msg()
        data class CreateButtonAvailability(val value: Boolean): Msg()
    }
    private inner class ExecutorImpl: CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeAction(action: Action) {
            when (action) {
                is Action.SaveUser -> {
                    scope.launch {
                        addUserUseCase(state().toUser())
                            .onSuccess {
                                publish(Label.OnSave)
                            }
                    }
                }
            }
        }

        override fun executeIntent(intent: Intent) {
            when (intent) {
                is Intent.NicknameEdit -> {
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
                is Intent.PhoneEdit -> {
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
                is Intent.PasswordEdit -> {
                    dispatch(Msg.Password(intent.value))
                    dispatch(Msg.PasswordErrorValue(passwordFieldValidate(intent.value)))
                    dispatch(
                        Msg.CreateButtonAvailability(isCreateAvailable())
                    )
                }
                Intent.Save -> forward(Action.SaveUser)
                Intent.ToggleIsAdminStatus -> dispatch(Msg.IsAdmin(!state().isAdmin))
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
    private object ReducerImpl: Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when(msg){
            is Msg.NicknameValue -> copy(nicknameTextField = nicknameTextField.copy(value = msg.value))
            is Msg.NicknameErrorValue -> copy(nicknameTextField = nicknameTextField.copy(error = msg.value))
            is Msg.PhoneValue -> copy(phoneTextField = phoneTextField.copy(value = msg.value))
            is Msg.PhoneErrorValue -> copy(phoneTextField = phoneTextField.copy(error = msg.value))
            is Msg.Password -> copy(passwordTextField = passwordTextField.copy(value = msg.value))
            is Msg.PasswordErrorValue -> copy(passwordTextField = passwordTextField.copy(error = msg.value))
            is Msg.IsAdmin -> copy(isAdmin = msg.value)
            is Msg.CreateButtonAvailability -> copy(isCreateButtonEnabled = msg.value)
        }
    }
}