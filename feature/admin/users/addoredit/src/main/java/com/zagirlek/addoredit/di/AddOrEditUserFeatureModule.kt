package com.zagirlek.addoredit.di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.zagirlek.addoredit.AddOrEditUserScreen
import com.zagirlek.addoredit.cmp.AddOrEditUserScreenComponent
import com.zagirlek.user.di.UserDomainModule

class AddOrEditUserFeatureModule(
    private val storeFactory: StoreFactory,
    private val userDomainModule: UserDomainModule
) {
    fun getAddUserComponent(
        componentContext: ComponentContext,
        userId: Long? = null,
        onFinish: () -> Unit
    ): AddOrEditUserScreen =
        AddOrEditUserScreenComponent(
            componentContext = componentContext,
            storeFactory = storeFactory,
            userId = userId,
            addUserUseCase = userDomainModule.addUserUseCase(),
            isNicknameUniqueUseCase = userDomainModule.getUserByNickname(),
            isPhoneUniqueUseCase = userDomainModule.getUserByPhone(),
            getUserByIdUseCase = userDomainModule.getUserByIdUseCase(),
            editUserUseCase = userDomainModule.editUserUseCase(),
            onFinish = onFinish
        )
}