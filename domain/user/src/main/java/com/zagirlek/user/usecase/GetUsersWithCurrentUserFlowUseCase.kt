package com.zagirlek.user.usecase

import com.zagirlek.authmanager.AuthManager
import com.zagirlek.common.utils.runCatchingCancellable
import com.zagirlek.user.model.UsersFlowModel
import com.zagirlek.user.repository.UserRepository
import kotlinx.coroutines.flow.combine

class GetUsersWithCurrentUserFlowUseCase(
    private val authManager: AuthManager,
    private val userRepository: UserRepository
) {
    operator fun invoke(query: String?): Result<UsersFlowModel> = runCatchingCancellable {
        val currUserFlow = authManager.getCurrUserFlow()
        val allUsersFlow = combine(
            currUserFlow,
            userRepository.searchUsersFlow(query)
        ) { user, usersList ->
            usersList.filterNot { it.id == user?.id }
        }

        UsersFlowModel(
            currentUserFlow = currUserFlow,
            allUsersListFlow = allUsersFlow
        )
    }
}