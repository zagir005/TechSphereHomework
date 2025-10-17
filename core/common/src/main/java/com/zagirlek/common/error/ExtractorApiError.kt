package com.zagirlek.common.error

sealed class ExtractorApiError(
        message: String? = null,
        cause: Throwable? = null
    ): Throwable(message, cause) {
    object UsageLimitReached:
        ExtractorApiError(message = "Достигнут лимит запросов к серверу")
    object RateLimitReached:
        ExtractorApiError(message = "Слишком много запросов к серверу за последние 60 секунд")
    object ServerError:
        ExtractorApiError(message = "Ошибка на стороне сервера. Попробуйте позже")
    object ResourceNotFound:
        ExtractorApiError(message = "Не найден ресурс")
    data class UnknownApiError(override val cause: Throwable? = null):
        ExtractorApiError(message = "Неизвестная ошибка API. Попробуйте позже")
    data class UnknownError(override val cause: Throwable? = null) :
        ExtractorApiError(message = "Неизвестная ошибка")
}