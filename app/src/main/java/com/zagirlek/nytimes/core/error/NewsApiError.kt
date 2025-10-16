package com.zagirlek.nytimes.core.error

sealed class NewsApiError(
        message: String? = null,
        cause: Throwable? = null
    ): Throwable(message, cause) {
        object TooManyRequests:
            NewsApiError(message = "Слишком много запросов к API. Попробуйте позже")
        object ServerError:
            NewsApiError(message = "Ошибка на стороне сервера. Попробуйте позже")
        object Unauthorized:
            NewsApiError(message = "Введен не валидный API ключ")
        object InvalidId:
            NewsApiError(message = "Новость с таким ID не существует")
        data class UnknownApiError(override val cause: Throwable? = null):
            NewsApiError("Неизвестная ошибка API. Попробуйте позже")
        data class UnknownError(override val cause: Throwable? = null):
            NewsApiError("Неизвестная ошибка")
    }