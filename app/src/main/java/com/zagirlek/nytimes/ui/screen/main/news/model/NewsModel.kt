package com.zagirlek.nytimes.ui.screen.main.news.model

import androidx.paging.PagingData
import com.zagirlek.nytimes.core.model.NewsCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class NewsModel(
    val newsPages: Flow<PagingData<Article>> = emptyFlow(),
    val selectedCategory: NewsCategory? = null,
    val searchFieldValue: String? = null
)
