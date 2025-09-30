package com.zagirlek.nytimes.domain.model

import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.data.local.news.entity.ArticleLiteEntity

data class ArticlesByCategory(
    val category: NewsCategory,
    val articles: List<ArticleLiteEntity>
)