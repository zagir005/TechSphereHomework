package com.zagirlek.nytimes.core.error


import retrofit2.HttpException

fun Throwable.toNewsApiError(): NewsApiError =
    if(this is HttpException)
        when(this.code()){
            401 -> NewsApiError.Unauthorized
            422 -> NewsApiError.InvalidId
            429 -> NewsApiError.TooManyRequests
            500 -> NewsApiError.ServerError
            else -> NewsApiError.UnknownApiError(cause = this)
        }
    else NewsApiError.UnknownError(cause = this)

fun Throwable.toExtractorApiError(): ExtractorApiError =
    if (this is HttpException)
        when(this.code()){
            402 -> ExtractorApiError.UsageLimitReached
            404 -> ExtractorApiError.ResourceNotFound
            429 -> ExtractorApiError.RateLimitReached
            500 -> ExtractorApiError.ServerError
            else -> ExtractorApiError.UnknownApiError(cause = this)
        }
    else ExtractorApiError.UnknownError(cause = this)