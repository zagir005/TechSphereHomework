package com.zagirlek.root

import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.value.Value
import com.zagirlek.list.UserListScreen

interface AdminRootComponent {
    val pages: Value<ChildPages<*, Child>>
    fun selectPage(index: Int)
    sealed class Child(){
        data class UserList(val component: UserListScreen): Child()
        data class Dashboard(val component: DashboardRootComponent): Child()
    }
}