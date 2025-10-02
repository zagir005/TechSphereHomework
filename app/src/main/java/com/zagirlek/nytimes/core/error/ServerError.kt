package com.zagirlek.nytimes.core.error

import retrofit2.HttpException

class ServerError(val msg: String = "Ошибка с сервера"): Throwable(message = msg)

