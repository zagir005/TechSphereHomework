package com.zagirlek.user.usecase

import com.zagirlek.common.model.User
import com.zagirlek.user.repository.UserRepository

class ToggleBlockUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User){
        userRepository.update(
            model = user.copy(
                isBlocked = !user.isBlocked
            )
        )
    }
}