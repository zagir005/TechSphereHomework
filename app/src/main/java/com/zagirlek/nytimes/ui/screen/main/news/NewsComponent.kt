package com.zagirlek.nytimes.ui.screen.main.news

import androidx.paging.PagingData
import com.zagirlek.nytimes.domain.model.ArticleLite
import kotlinx.coroutines.flow.Flow

interface NewsComponent {
    val newsList: Flow<PagingData<ArticleLite>>
}