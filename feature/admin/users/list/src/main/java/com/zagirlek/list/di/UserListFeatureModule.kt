package com.zagirlek.list.di

import com.arkivanov.decompose.ComponentContext
import com.zagirlek.list.DefaultUserListScreen
import com.zagirlek.list.UserListScreen

class UserListFeatureModule(

) {
    fun getUserListScreen(
        context: ComponentContext
    ): UserListScreen =
        DefaultUserListScreen(componentContext = context)
}