package com.zagirlek.nytimes.data.mapper

import com.zagirlek.nytimes.core.utils.toLocalDateTime
import com.zagirlek.nytimes.data.local.news.entity.ArticleLiteEntity
import com.zagirlek.nytimes.data.local.news.entity.ArticleLiteWithStatusEntity
import com.zagirlek.nytimes.domain.model.ArticleFull
import com.zagirlek.nytimes.domain.model.ArticleLite
import com.zagirlek.nytimes.domain.model.ArticleLiteWithStatus

fun ArticleLite.toArticleFull(
    fullText: String
): ArticleFull = ArticleFull(
    articleId = articleId,
    link = link,
    title = title,
    fullText = fullText,
    description = description,
    category = category,
    sourceName = sourceName,
    sourceIconUrl = sourceIconUrl,
    creator = creator,
    imageUrl = imageUrl,
    pubDate = pubDate
)

fun ArticleLite.toArticleWithStatus(
    isFavorite: Boolean = false,
    isRead: Boolean = false
): ArticleLiteWithStatus = ArticleLiteWithStatus(
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
    isFavorite = isFavorite,
    isRead = isRead
)

fun ArticleLiteWithStatusEntity.toDomain(): ArticleLiteWithStatus = with(article){
    ArticleLiteWithStatus(
        articleId = articleId,
        link = link,
        title = title,
        description = description,
        category = category,
        sourceName = sourceName,
        sourceIconUrl = sourceIconUrl,
        creator = creator,
        imageUrl = imageUrl,
        pubDate = pubDate.toLocalDateTime(),
        isFavorite = isFavorite ?: false,
        isRead = isRead ?: false
    )
}

fun ArticleLiteEntity.toDomain(): ArticleLite = ArticleLite(
    articleId = articleId,
    link = link,
    title = title,
    description = description ?: "У новости нету описания",
    category = category,
    sourceName = sourceName,
    sourceIconUrl = sourceIconUrl,
    creator = creator,
    imageUrl = imageUrl,
    pubDate = pubDate.toLocalDateTime()
)