package com.zagirlek.user.usecase

import com.zagirlek.authmanager.AuthManager
import com.zagirlek.common.utils.runCatchingCancellable
import com.zagirlek.user.model.UsersFlowModel
import com.zagirlek.user.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class GetUsersWithCurrentUserFlowUseCase(
    private val authManager: AuthManager,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(query: String?): Result<UsersFlowModel> = runCatchingCancellable {
        val currUserFlow = authManager.getCurrUserFlow()
        UsersFlowModel(
            current = currUserFlow,
            allUsersList = userRepository.searchUsersFlow(query)
                .map {
                    it.filterNot { user -> user.id == currUserFlow.first().id}
                }
        )
    }
}