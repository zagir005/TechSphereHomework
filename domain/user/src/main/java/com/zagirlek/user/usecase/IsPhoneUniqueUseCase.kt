package com.zagirlek.user.usecase

import com.zagirlek.user.repository.UserRepository

class IsPhoneUniqueUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(phone: String): Boolean {
        val userList = userRepository.getAllList(phone)
        return userList.none { it.phone.contentEquals(other = phone, ignoreCase = true) }
    }
}