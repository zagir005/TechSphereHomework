package com.zagirlek.news.mapper

import com.zagirlek.common.model.NewsCategory
import com.zagirlek.common.utils.toEpochMillis
import com.zagirlek.common.utils.toLocalDateTime
import com.zagirlek.local.news.entity.ArticleFullEntity
import com.zagirlek.local.news.entity.ArticleFullWithStatusEntity
import com.zagirlek.news.model.ArticleFull
import com.zagirlek.news.model.ArticleFullWithStatus

fun ArticleFull.toEntity(): ArticleFullEntity = ArticleFullEntity(
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
    pubDate = pubDate.toEpochMillis(),
)

fun ArticleFull.toArticleWithStatus(
    isFavorite: Boolean?,
    isRead: Boolean?
): ArticleFullWithStatus = ArticleFullWithStatus(
    articleId = articleId,
    link = link,
    title = title,
    fullText = fullText,
    description = description,
    category = category ?: NewsCategory.OTHER,
    sourceName = sourceName,
    sourceIconUrl = sourceIconUrl,
    creator = creator,
    imageUrl = imageUrl,
    pubDate = pubDate,
    isFavorite = isFavorite ?: false,
    isRead = isRead ?: false
)

fun ArticleFullWithStatusEntity.toDomain(): ArticleFullWithStatus = with(article){
    ArticleFullWithStatus(
        articleId = articleId,
        link = link,
        title = title,
        fullText = fullText,
        description = description,
        category = category ?: NewsCategory.OTHER,
        sourceName = sourceName,
        sourceIconUrl = sourceIconUrl,
        creator = creator,
        imageUrl = imageUrl,
        pubDate = pubDate.toLocalDateTime(),
        isFavorite = isFavorite ?: false,
        isRead = isRead ?: false
    )
}

fun ArticleFullEntity.toDomain(): ArticleFull = ArticleFull(
    articleId = articleId,
    link = link,
    title = title,
    fullText = fullText,
    description = description ?: "У новости нету описания",
    category = category,
    sourceName = sourceName,
    sourceIconUrl = sourceIconUrl,
    creator = creator,
    imageUrl = imageUrl,
    pubDate = pubDate.toLocalDateTime()
)