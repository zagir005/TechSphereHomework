package com.zagirlek.latest.model

import androidx.paging.PagingData
import androidx.paging.map
import com.zagirlek.common.model.Article
import com.zagirlek.common.utils.timeAgoOrDate
import com.zagirlek.latest.store.LatestNewsStore
import com.zagirlek.news.model.ArticleLiteWithStatus
import com.zagirlek.ui.elements.newscategory.toUiCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal fun ArticleLiteWithStatus.toArticleItem(): Article = Article(
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
internal fun Flow<PagingData<ArticleLiteWithStatus>>.toArticleFlow() = map { list ->
    list.map {
        it.toArticleItem()
    }
}

internal fun LatestNewsStore.State.toModel(): LatestNewsModel = LatestNewsModel(
    newsPages = newsFlow,
    selectedCategory = selectedCategory?.toUiCategory(),
    searchFieldValue = searchField,
)

