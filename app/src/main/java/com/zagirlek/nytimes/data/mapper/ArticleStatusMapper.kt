package com.zagirlek.nytimes.data.mapper

import com.zagirlek.nytimes.data.local.news.entity.ArticleStatusEntity
import com.zagirlek.nytimes.domain.model.ArticleStatus

fun ArticleStatusEntity.toDomain(): ArticleStatus = ArticleStatus(
    articleId = articleId,
    isRead = isRead,
    isFavorite = isFavorite
)