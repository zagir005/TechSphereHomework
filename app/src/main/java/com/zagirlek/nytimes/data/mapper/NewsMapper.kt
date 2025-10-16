package com.zagirlek.nytimes.data.mapper

import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.core.utils.toEpochMillis
import com.zagirlek.nytimes.core.utils.toLocalDateTime
import com.zagirlek.nytimes.data.local.news.entity.ArticleLiteEntity
import com.zagirlek.nytimes.data.network.news.dto.ArticleDTO
import com.zagirlek.nytimes.domain.model.ArticleLite


fun ArticleDTO.toEntity(): ArticleLiteEntity = ArticleLiteEntity(
    articleId = articleId,
    link = link,
    title = title,
    description = description,
    sourceName = sourceName,
    sourceIconUrl = sourceIconUrl,
    category = category?.firstOrNull() ?: NewsCategory.OTHER,
    creator = creator?.firstOrNull(),
    imageUrl = imageUrl.orEmpty(),
    pubDate = pubDate.toLocalDateTime().toEpochMillis()
)

fun ArticleDTO.toDomain(): ArticleLite = ArticleLite(
    articleId = articleId,
    link = link,
    title = title,
    description = description,
    category = category?.firstOrNull() ?: NewsCategory.OTHER,
    sourceName = sourceName,
    sourceIconUrl = sourceIconUrl,
    creator = creator?.firstOrNull() ?: "",
    imageUrl = imageUrl.orEmpty(),
    pubDate = pubDate.toLocalDateTime(),
)


