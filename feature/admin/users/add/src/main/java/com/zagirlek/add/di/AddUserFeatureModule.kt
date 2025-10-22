package com.zagirlek.add.di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.zagirlek.add.AddUserScreen
import com.zagirlek.add.cmp.AddUserScreenComponent
import com.zagirlek.user.di.UserDomainModule

class AddUserFeatureModule(
    private val storeFactory: StoreFactory,
    private val userDomainModule: UserDomainModule
) {
    fun getAddUserComponent(
        componentContext: ComponentContext,
        onSave: () -> Unit
    ): AddUserScreen =
        AddUserScreenComponent(
            componentContext = componentContext,
            storeFactory = storeFactory,
            addUserUseCase = userDomainModule.addUserUseCase(),
            isNicknameUniqueUseCase = userDomainModule.getUserByNickname(),
            isPhoneUniqueUseCase = userDomainModule.getUserByPhone(),
            onSave = onSave
        )
}