package com.zagirlek.nytimes.core.error

sealed class AuthError(
        message: String? = null,
        cause: Throwable? = null
    ): Throwable(message, cause) {
    object ServerError : AuthError(message = "Ошибка на стороне сервера. Попробуйте позже")
    object InvalidCredentials : AuthError(message = "Не найден ресурс")
    object NoNetworkConnection : AuthError(message = "Отсутствует соединение")
    data class UnknownError(override val cause: Throwable? = null) :
        AuthError("Неизвестная ошибка. ${cause?.message}")
}