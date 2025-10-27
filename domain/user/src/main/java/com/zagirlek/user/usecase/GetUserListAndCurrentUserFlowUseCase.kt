package com.zagirlek.user.usecase

import com.zagirlek.authmanager.AuthManager
import com.zagirlek.user.model.UsersFlowModel
import com.zagirlek.user.repository.UserRepository
import kotlinx.coroutines.flow.combine

class GetUserListAndCurrentUserFlowUseCase(
    private val authManager: AuthManager,
    private val userRepository: UserRepository
) {
    operator fun invoke(query: String?): Result<UsersFlowModel> =
        authManager.getCurrUserFlow().fold(
            onSuccess = { currUserFlow ->
                val allUsersFlow = combine(
                    flow = currUserFlow,
                    flow2 = userRepository.getAllFlow(query)
                ) { user, usersList ->
                    usersList.filterNot { it.id == user.id }
                }
                Result.success(
                    UsersFlowModel(
                        currentUserFlow = currUserFlow,
                        allUsersListFlow = allUsersFlow
                    )
                )
            },
            onFailure = {
                Result.failure(it)
            }
        )
}