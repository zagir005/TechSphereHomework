package com.zagirlek.nytimes.ui.screen.main.news.model

import com.zagirlek.nytimes.ui.screen.main.news.store.NewsStore

fun NewsStore.State.toModel(): NewsModel = NewsModel(
    newsPages = newsFlow,
    selectedCategory = selectedCategory,
    searchFieldValue = searchField
)