package com.zagirlek.nytimes.data.mapper

import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.core.utils.toEpochMillis
import com.zagirlek.nytimes.core.utils.toLocalDateTime
import com.zagirlek.nytimes.data.local.news.entity.ArticleLiteEntity
import com.zagirlek.nytimes.data.network.news.dto.ArticleDTO
import com.zagirlek.nytimes.domain.model.ArticleFull
import com.zagirlek.nytimes.domain.model.ArticleLite

fun ArticleDTO.toEntity(): ArticleLiteEntity = ArticleLiteEntity(
    articleId = articleId,
    link = link,
    title = title,
    description = description,
    sourceName = sourceName,
    sourceIconUrl = sourceIconUrl,
    category = category.firstOrNull() ?: NewsCategory.OTHER,
    creator = creator.firstOrNull().orEmpty(),
    imageUrl = imageUrl,
    pubDate = pubDate.toLocalDateTime().toEpochMillis(),
    isFavorite = false,
    isRead = false
)

fun ArticleLiteEntity.toDomain(): ArticleLite = ArticleLite(
    articleId = articleId,
    link = link,
    title = title,
    description = description,
    category = category,
    sourceName = sourceName,
    sourceIconUrl = sourceIconUrl,
    creator = listOf(creator),
    imageUrl = imageUrl,
    pubDate = pubDate.toLocalDateTime(),
    isFavorite = isFavorite,
    isRead = isRead
)

fun ArticleDTO.toDomain(): ArticleLite = ArticleLite(
    articleId = articleId,
    link = link,
    title = title,
    description = description,
    category = category.firstOrNull() ?: NewsCategory.OTHER,
    sourceName = sourceName,
    sourceIconUrl = sourceIconUrl,
    creator = creator,
    imageUrl = imageUrl,
    pubDate = pubDate.toLocalDateTime(),
    isFavorite = false,
    isRead = false
)

fun ArticleLite.toArticleFull(fullText: String): ArticleFull = ArticleFull(
    articleId = articleId,
    link = link,
    title = title,
    description = description,
    category = category,
    sourceName = sourceName,
    sourceIconUrl = sourceIconUrl,
    creator = creator,
    imageUrl = imageUrl,
    pubDate = pubDate,
    fullText = fullText
)

