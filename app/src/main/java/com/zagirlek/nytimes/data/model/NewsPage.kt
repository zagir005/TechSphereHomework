package com.zagirlek.nytimes.data.model

import com.zagirlek.nytimes.domain.model.ArticleLite

data class NewsPage(
    val totalResults: Int,
    val newsList: List<ArticleLite>,
    val nextPage: String
)