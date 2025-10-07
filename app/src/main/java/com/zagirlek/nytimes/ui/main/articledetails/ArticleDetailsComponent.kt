package com.zagirlek.nytimes.ui.main.articledetails

import com.zagirlek.nytimes.domain.model.ArticleFullWithStatus
import kotlinx.coroutines.flow.StateFlow

interface ArticleDetailsComponent{
    val article: StateFlow<ArticleFullWithStatus?>
    fun toggleFavoriteStatus()
    fun toggleReadStatus()
}