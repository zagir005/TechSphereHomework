package com.zagirlek.remote.extractor

import com.zagirlek.remote.extractor.dto.ExtractedArticleDTO

interface RemoteNewsExtractorSource {
    suspend fun extractArticleText(
        link: String
    ): Result<ExtractedArticleDTO>
}