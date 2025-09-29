package com.zagirlek.nytimes.domain.model

data class NewsPage(
    val nextPage: String,
    val articleLiteList: List<ArticleLite>
)
