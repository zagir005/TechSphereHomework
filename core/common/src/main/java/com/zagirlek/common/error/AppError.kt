package com.zagirlek.common.error

sealed class AppError(
    message: String? = null,
    cause: Throwable? = null
): Throwable(message = message, cause = cause) {
    object NoNetworkConnection:
        AppError(message = "Отсутствует интернет соединение")
    object TryingInsertDuplicate:
        AppError(message = "Попытка добавить дубликат")
    data class TryingUpdateWithDuplicateValues(
        override val message: String? = "Новые данные уже содержатся в другом объекте"
    ): AppError(message = message)
}