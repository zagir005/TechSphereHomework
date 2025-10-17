package com.zagirlek.common.error

sealed class AppError(
    message: String? = null,
    cause: Throwable? = null
): Throwable(message = message, cause = cause) {
    object NoNetworkConnection: AppError(message = "Отсутствует интернет соединение")
}