package com.zagirlek.nytimes.domain.repository

import androidx.paging.PagingData
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.domain.model.ArticleFullWithStatus
import com.zagirlek.nytimes.domain.model.ArticleLiteWithStatus
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNewsPager(
        category: NewsCategory? = null,
        titleQuery: String? = null
    ): Flow<PagingData<ArticleLiteWithStatus>>

    fun getFavoriteNewsPager(
        category: NewsCategory? = null,
        titleQuery: String? = null
    ): Flow<PagingData<ArticleFullWithStatus>>
}