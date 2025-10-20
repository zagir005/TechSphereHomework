package com.zagirlek.list

import com.arkivanov.decompose.ComponentContext

internal class DefaultUserListScreen(
    componentContext: ComponentContext
): UserListScreen, ComponentContext by componentContext{
}