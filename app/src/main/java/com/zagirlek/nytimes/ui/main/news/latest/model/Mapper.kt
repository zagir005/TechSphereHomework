package com.zagirlek.nytimes.ui.main.news.latest.model

import androidx.paging.PagingData
import androidx.paging.map
import com.zagirlek.common.utils.timeAgoOrDate
import com.zagirlek.nytimes.domain.model.ArticleLiteWithStatus
import com.zagirlek.nytimes.ui.main.news.latest.store.LatestNewsStore
import com.zagirlek.nytimes.ui.main.news.model.Article
import com.zagirlek.ui.elements.toUiCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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
fun Flow<PagingData<ArticleLiteWithStatus>>.toArticleFlow() = map { list ->
    list.map {
        it.toArticleItem()
    }
}

fun LatestNewsStore.State.toModel(): LatestNewsModel = LatestNewsModel(
    newsPages = newsFlow,
    selectedCategory = selectedCategory?.toUiCategory(),
    searchFieldValue = searchField,
)

