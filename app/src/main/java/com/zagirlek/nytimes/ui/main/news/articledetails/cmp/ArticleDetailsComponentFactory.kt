package com.zagirlek.nytimes.ui.main.news.articledetails.cmp

import com.arkivanov.decompose.ComponentContext
import com.zagirlek.nytimes.ui.main.news.articledetails.ArticleDetailsComponent

interface ArticleDetailsComponentFactory {
    fun create(context: ComponentContext, articleId: String): ArticleDetailsComponent
}