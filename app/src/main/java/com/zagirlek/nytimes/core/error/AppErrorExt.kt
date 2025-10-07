package com.zagirlek.nytimes.core.error


import com.zagirlek.nytimes.core.error.AppError.UnknownError
import retrofit2.HttpException

fun HttpException.toNewsApiError(): NewsApiError =
    when(this.code()){
        429 -> NewsApiError.TooManyRequests
        401 -> NewsApiError.Unauthorized
        500 -> NewsApiError.ServerError
        422 -> NewsApiError.InvalidId
        else -> NewsApiError.UnknownError(cause = this)
    }

fun HttpException.toExtractorApiError(): ExtractorApiError =
    when(this.code()){
        429 -> ExtractorApiError.RateLimitReached
        402 -> ExtractorApiError.UsageLimitReached
        500 -> ExtractorApiError.ServerError
        404 -> ExtractorApiError.ResourceNotFound
        else -> ExtractorApiError.UnknownError(cause = this)
    }

fun Throwable.toNewsApiError() =
    when(this){
        is HttpException -> toNewsApiError()
        else -> UnknownError(cause = this)
    }

fun Throwable.toExtractorApiError(): ExtractorApiError =
    when(this){
        is HttpException -> toExtractorApiError()
        else -> ExtractorApiError.UnknownError(cause = this)
    }