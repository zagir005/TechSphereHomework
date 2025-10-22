package com.zagirlek.user.di

import com.zagirlek.user.repository.UserRepository
import com.zagirlek.user.usecase.AddUserUseCase
import com.zagirlek.user.usecase.DeleteUserByIdUseCase
import com.zagirlek.user.usecase.EditUserUseCase
import com.zagirlek.user.usecase.GetUserByIdUseCase
import com.zagirlek.user.usecase.IsNicknameUniqueUseCase
import com.zagirlek.user.usecase.IsPhoneUniqueUseCase
import com.zagirlek.user.usecase.GetUserListFlowUseCase
import com.zagirlek.user.usecase.ToggleBlockUserUseCase

class UserDomainModule(
    private val userRepository: UserRepository
) {
    fun getUserListFlowUseCase(): GetUserListFlowUseCase = GetUserListFlowUseCase(userRepository)
    fun addUserUseCase(): AddUserUseCase = AddUserUseCase(userRepository)
    fun editUserUseCase(): EditUserUseCase = EditUserUseCase(userRepository)
    fun deleteUserByIdUseCase(): DeleteUserByIdUseCase = DeleteUserByIdUseCase(userRepository)
    fun blockUserUseCase(): ToggleBlockUserUseCase = ToggleBlockUserUseCase(userRepository)
    fun getUserByIdUseCase(): GetUserByIdUseCase = GetUserByIdUseCase(userRepository)
    fun getUserByPhone(): IsPhoneUniqueUseCase = IsPhoneUniqueUseCase(userRepository)
    fun getUserByNickname(): IsNicknameUniqueUseCase = IsNicknameUniqueUseCase(userRepository)
}