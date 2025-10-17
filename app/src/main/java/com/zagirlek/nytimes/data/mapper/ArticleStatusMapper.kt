package com.zagirlek.nytimes.data.mapper

import com.zagirlek.local.news.entity.ArticleStatusEntity
import com.zagirlek.news.model.ArticleStatus

fun ArticleStatusEntity.toDomain(): ArticleStatus = ArticleStatus(
    articleId = articleId,
    isRead = isRead,
    isFavorite = isFavorite
)