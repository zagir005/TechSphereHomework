package com.zagirlek.user.di

import com.zagirlek.user.repository.UserRepository
import com.zagirlek.user.usecase.AddUserUseCase
import com.zagirlek.user.usecase.BlockUserUseCase
import com.zagirlek.user.usecase.DeleteUserByIdUseCase
import com.zagirlek.user.usecase.EditUserUseCase
import com.zagirlek.user.usecase.GetUserListFlowUseCase

class UserDomainModule(
    private val userRepository: UserRepository
) {
    fun getUserListFlowUseCase(): GetUserListFlowUseCase = GetUserListFlowUseCase(userRepository)
    fun addUserUseCase(): AddUserUseCase = AddUserUseCase(userRepository)
    fun editUserUseCase(): EditUserUseCase = EditUserUseCase(userRepository)
    fun deleteUserByIdUseCase(): DeleteUserByIdUseCase = DeleteUserByIdUseCase(userRepository)
    fun blockUserUseCase(): BlockUserUseCase = BlockUserUseCase(userRepository)
}