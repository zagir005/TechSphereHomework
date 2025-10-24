package com.zagirlek.user.usecase

import com.zagirlek.user.repository.UserRepository

class DeleteUserByIdUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(id: Long) {
        userRepository.deleteById(id)
    }
}