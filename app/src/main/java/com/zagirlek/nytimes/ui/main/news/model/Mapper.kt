package com.zagirlek.nytimes.ui.main.news.model

import com.zagirlek.nytimes.core.ui.model.Article
import com.zagirlek.nytimes.core.utils.timeAgoOrDate
import com.zagirlek.nytimes.domain.model.ArticleLiteWithStatus
import com.zagirlek.nytimes.ui.main.news.store.NewsStore

fun ArticleLiteWithStatus.toArticleItem(): Article = Article(
    articleId = articleId,
    title = title,
    category = category,
    imageUrl = imageUrl,
    creator = creator?.ifBlank { sourceName } ?: sourceName,
    pubDate = pubDate.timeAgoOrDate(),
    readTime = "5 мин",
    description = description,
    isRead = isRead,
    isFavorite = isFavorite,
)

fun NewsStore.State.toModel(): NewsModel = NewsModel(
    newsPages = newsFlow,
    selectedCategory = selectedCategory,
    searchFieldValue = searchField,
)

