package com.zagirlek.nytimes.domain.usecase

import com.zagirlek.nytimes.domain.model.NewsFilter
import com.zagirlek.nytimes.domain.model.NewsPage

fun interface GetLatestNewsUseCase {
    suspend operator fun invoke(
        newsFilter: NewsFilter
    ): Result<NewsPage>
}