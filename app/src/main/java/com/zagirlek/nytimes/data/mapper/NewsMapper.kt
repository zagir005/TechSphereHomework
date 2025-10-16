package com.zagirlek.nytimes.data.mapper

import com.zagirlek.common.model.NewsCategory
import com.zagirlek.common.utils.toEpochMillis
import com.zagirlek.common.utils.toLocalDateTime
import com.zagirlek.local.news.entity.ArticleLiteEntity
import com.zagirlek.nytimes.domain.model.ArticleLite
import com.zagirlek.remote.news.dto.ArticleDTO


fun ArticleDTO.toEntity(): ArticleLiteEntity = ArticleLiteEntity(
    articleId = articleId,
    link = link,
    title = title,
    description = description,
    sourceName = sourceName,
    sourceIconUrl = sourceIconUrl,
    category = category?.let { NewsCategory.valueOf(it.first().name) } ?: NewsCategory.OTHER,
    creator = creator?.firstOrNull(),
    imageUrl = imageUrl.orEmpty(),
    pubDate = pubDate.toLocalDateTime().toEpochMillis()
)

fun ArticleDTO.toDomain(): ArticleLite = ArticleLite(
    articleId = articleId,
    link = link,
    title = title,
    description = description,
    category = category?.let { NewsCategory.valueOf(it.first().name) } ?: NewsCategory.OTHER,
    sourceName = sourceName,
    sourceIconUrl = sourceIconUrl,
    creator = creator?.firstOrNull() ?: "",
    imageUrl = imageUrl.orEmpty(),
    pubDate = pubDate.toLocalDateTime(),
)


