package com.zagirlek.user.model

import com.zagirlek.common.model.User
import kotlinx.coroutines.flow.Flow

data class UsersFlowModel(
    val currentUserFlow: Flow<User?>,
    val allUsersListFlow: Flow<List<User>>
)
