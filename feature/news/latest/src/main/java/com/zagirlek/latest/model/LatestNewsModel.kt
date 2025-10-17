package com.zagirlek.latest.model

import androidx.paging.PagingData
import com.zagirlek.common.model.Article
import com.zagirlek.ui.elements.NewsCategoryUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class LatestNewsModel(
    val newsPages: Flow<PagingData<Article>> = emptyFlow(),
    val selectedCategory: NewsCategoryUi? = null,
    val searchFieldValue: String? = null
)
