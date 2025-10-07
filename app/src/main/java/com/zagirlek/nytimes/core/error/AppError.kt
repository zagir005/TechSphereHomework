package com.zagirlek.nytimes.core.error

sealed class AppError(
    message: String? = null,
    cause: Throwable? = null
): Throwable(message, cause) {
    data class UnknownError(override val cause: Throwable? = null): AppError(message = "Неизвестная ошибка")
}

