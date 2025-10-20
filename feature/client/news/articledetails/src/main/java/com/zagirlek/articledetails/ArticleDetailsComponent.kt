package com.zagirlek.articledetails

import com.zagirlek.articledetails.store.ArticleDetailsStore
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

interface ArticleDetailsComponent{
    val article: StateFlow<ArticleDetailsStore.State>
    fun toggleFavoriteStatus()
    fun toggleReadStatus()
    fun retry()

    @Serializable
    data class ArticleDetailsConfig(
        val articleId: String
    )
}