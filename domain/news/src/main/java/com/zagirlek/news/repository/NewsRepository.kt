package com.zagirlek.news.repository

import androidx.paging.PagingData
import com.zagirlek.common.model.NewsCategory
import com.zagirlek.news.model.ArticleLiteWithStatus
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNewsPager(
        category: NewsCategory? = null,
        titleQuery: String? = null
    ): Flow<PagingData<ArticleLiteWithStatus>>

    fun getFavoriteNewsFlow(
        category: NewsCategory? = null,
        titleQuery: String? = null
    ): Flow<List<ArticleLiteWithStatus>>
}