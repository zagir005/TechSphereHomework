package com.zagirlek.user.usecase

import android.database.sqlite.SQLiteConstraintException
import com.zagirlek.common.error.AppError
import com.zagirlek.common.model.User
import com.zagirlek.common.utils.mapError
import com.zagirlek.common.utils.runCatchingCancellable
import com.zagirlek.user.repository.UserRepository

class AddUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User): Result<User> = runCatchingCancellable {
        val id = userRepository.add(user)
        userRepository.getById(id) ?:
            throw IllegalStateException("User с id=$id существует, но не найден")
    }.mapError {
        if (it is SQLiteConstraintException) AppError.TryingInsertDuplicate else it
    }
}