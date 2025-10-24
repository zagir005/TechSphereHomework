package com.zagirlek.auth.usecase

import com.zagirlek.authmanager.AuthManager
import com.zagirlek.common.model.User
import kotlinx.coroutines.flow.Flow

class GetCurrentUserFlowUseCase(
    private val authManager: AuthManager
) {
    suspend operator fun invoke(): Flow<User?>? =
        authManager.getCurrUserFlow()
}