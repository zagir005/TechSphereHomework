package com.zagirlek.nytimes.data.network.extractor

import com.zagirlek.nytimes.core.error.toExtractorApiError
import com.zagirlek.nytimes.core.utils.mapError
import com.zagirlek.nytimes.core.utils.runCatchingCancellable
import com.zagirlek.nytimes.data.network.extractor.dto.ExtractedArticleDTO
import com.zagirlek.nytimes.data.network.extractor.service.ExtractorService

class RemoteNewsExtractorSource(
    private val extractorService: ExtractorService
) {
    suspend fun extractArticleText(
        link: String
    ): Result<ExtractedArticleDTO> = runCatchingCancellable {
        extractorService.extractByUrl(link).article
    }.mapError {
        it.toExtractorApiError()
    }
}