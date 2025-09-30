package com.zagirlek.nytimes.ui.screen.main.news.cmp

import com.arkivanov.decompose.ComponentContext
import com.zagirlek.nytimes.core.model.NewsFilter
import com.zagirlek.nytimes.domain.usecase.GetPagingNewsUseCase
import com.zagirlek.nytimes.ui.screen.main.news.NewsComponent

class DefaultNewsComponent(
    componentContext: ComponentContext,
    getPagingNewsUseCase: GetPagingNewsUseCase
): ComponentContext by componentContext, NewsComponent{
    override val newsList = getPagingNewsUseCase(filter = NewsFilter())

}
