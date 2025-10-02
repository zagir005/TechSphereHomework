package com.zagirlek.nytimes.core.error

class NetworkError(val msg: String = "Проблемы с сетью"): Throwable(message = msg)