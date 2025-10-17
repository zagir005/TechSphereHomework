package com.zagirlek.remote.news

import com.zagirlek.common.model.NewsCategory
import com.zagirlek.remote.news.dto.NewsPageDTO

interface RemoteNewsSource {
    suspend fun getLatestNews(
        category: NewsCategory? = null,
        page: String? = null,
        titleQuery: String? = null,
        id: String? = null
    ): Result<NewsPageDTO>
}