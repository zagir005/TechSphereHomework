package com.zagirlek.list.di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.zagirlek.addoredit.di.AddOrEditUserFeatureModule
import com.zagirlek.list.UserListScreen
import com.zagirlek.list.cmp.UserListScreenComponent
import com.zagirlek.user.di.UserDomainModule

class UserListFeatureModule(
    private val storeFactory: StoreFactory,
    private val userDomainModule: UserDomainModule,
    private val addOrEditUserFeatureModule: AddOrEditUserFeatureModule,
) {
    fun getUserListScreen(
        context: ComponentContext
    ): UserListScreen =
        UserListScreenComponent(
            componentContext = context,
            storeFactory = storeFactory,
            getUserListFlowUseCase = userDomainModule.getUserListFlowUseCase(),
            addOrEditUserModule = addOrEditUserFeatureModule,
            deleteUserByIdUseCase = userDomainModule.deleteUserByIdUseCase()
        )
}