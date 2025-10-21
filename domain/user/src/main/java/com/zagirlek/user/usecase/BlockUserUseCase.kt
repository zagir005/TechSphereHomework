package com.zagirlek.user.usecase

import com.zagirlek.common.model.UserStatus
import com.zagirlek.user.model.User
import com.zagirlek.user.repository.UserRepository

class BlockUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User){
        userRepository.update(
            user = user.copy(
                status = UserStatus.BLOCKED
            )
        )
    }
}