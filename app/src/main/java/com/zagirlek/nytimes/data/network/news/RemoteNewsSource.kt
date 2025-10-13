package com.zagirlek.nytimes.data.network.news

import com.zagirlek.nytimes.core.error.toNewsApiError
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.core.utils.mapError
import com.zagirlek.nytimes.core.utils.runCatchingCancellable
import com.zagirlek.nytimes.data.network.news.dto.NewsPageDTO
import com.zagirlek.nytimes.data.network.news.service.NewsService

class RemoteNewsSource(
    private val newsService: NewsService,
) {
    suspend fun getLatestNews(
        category: NewsCategory? = null,
        page: String? = null,
        titleQuery: String? = null,
        id: String? = null
    ): Result<NewsPageDTO> = runCatchingCancellable {
        if (id != null)
            newsService.latest(
                id = id,
                domain = null,
                language = null
            )
        else
            newsService.latest(
                category = category?.let { listOf(category.serializedName) },
                page = page,
                titleQuery = titleQuery,
                id = id
            )
    }.mapError {
        it.toNewsApiError()
    }
}