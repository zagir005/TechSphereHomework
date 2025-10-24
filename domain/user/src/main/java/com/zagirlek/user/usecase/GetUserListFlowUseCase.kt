package com.zagirlek.user.usecase

import com.zagirlek.common.model.User
import com.zagirlek.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserListFlowUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(query: String?): Flow<List<User>> =
        userRepository.searchUsersFlow(query)
}