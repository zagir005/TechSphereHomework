package com.zagirlek.user.usecase

import android.database.sqlite.SQLiteConstraintException
import com.zagirlek.common.error.AppError
import com.zagirlek.common.model.User
import com.zagirlek.common.utils.mapError
import com.zagirlek.common.utils.runCatchingCancellable
import com.zagirlek.user.repository.UserRepository

class EditUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User): Result<User> = runCatchingCancellable {
        userRepository.update(user)
        userRepository.getById(user.id) ?: throw IllegalStateException(
            "User с id=${user.id} существует, но не найден"
        )
    }.mapError {
        if (it is SQLiteConstraintException) AppError.TryingUpdateWithDuplicateValues(
            message = "Номер телефона или логин уже используются у другого юзера"
        ) else it
    }
}