package com.zagirlek.nytimes.ui.main.news.latest.model

import androidx.paging.PagingData
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.core.ui.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class NewsModel(
    val newsPages: Flow<PagingData<Article>> = emptyFlow(),
    val selectedCategory: NewsCategory? = null,
    val searchFieldValue: String? = null
)
