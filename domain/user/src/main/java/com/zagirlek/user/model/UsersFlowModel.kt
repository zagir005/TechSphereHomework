package com.zagirlek.user.model

import com.zagirlek.common.model.User
import kotlinx.coroutines.flow.Flow

data class UsersFlowModel(
    val current: Flow<User>,
    val allUsersList: Flow<List<User>>
)
