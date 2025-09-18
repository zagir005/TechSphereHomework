package com.zagirlek.nytimes.ui.screen.main.news.cmp

import com.arkivanov.decompose.ComponentContext
import com.zagirlek.nytimes.ui.screen.main.news.NewsComponent

class DefaultNewsComponent(
    componentContext: ComponentContext
): ComponentContext by componentContext, NewsComponent