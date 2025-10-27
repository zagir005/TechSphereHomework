package com.zagirlek.user.usecase

import com.zagirlek.user.repository.UserRepository

class IsNicknameUniqueUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(nickname: String): Boolean {
        val userList = userRepository.getAllList(nickname)
        return userList.none { it.nickname.contentEquals(other = nickname, ignoreCase = true) }
    }
}