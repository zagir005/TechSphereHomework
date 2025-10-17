package com.zagirlek.remote.extractor

import com.zagirlek.common.error.ExtractorApiError
import com.zagirlek.common.utils.mapError
import com.zagirlek.common.utils.runCatchingCancellable
import com.zagirlek.remote.extractor.dto.ExtractedArticleDTO
import com.zagirlek.remote.extractor.service.ExtractorService
import retrofit2.HttpException

internal class RemoteNewsExtractorSourceImpl(
    private val extractorService: ExtractorService
): RemoteNewsExtractorSource {

    override suspend fun extractArticleText(
        link: String
    ): Result<ExtractedArticleDTO> = runCatchingCancellable {
        extractorService.extractByUrl(link).article
    }.mapError {
        it.toExtractorApiError()
    }

    private fun Throwable.toExtractorApiError(): ExtractorApiError =
        if (this is HttpException)
            when(this.code()){
                402 -> ExtractorApiError.UsageLimitReached
                404 -> ExtractorApiError.ResourceNotFound
                429 -> ExtractorApiError.RateLimitReached
                500 -> ExtractorApiError.ServerError
                else -> ExtractorApiError.UnknownApiError(cause = this)
            }
        else ExtractorApiError.UnknownError(cause = this)
}