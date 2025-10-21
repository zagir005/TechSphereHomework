package com.zagirlek.user.usecase

import com.zagirlek.user.model.User
import com.zagirlek.user.repository.UserRepository

class GetUserByIdUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(id: Long): User? =
        userRepository.getById(id)
}