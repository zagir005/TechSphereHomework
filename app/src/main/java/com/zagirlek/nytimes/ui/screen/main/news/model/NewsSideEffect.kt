package com.zagirlek.nytimes.ui.screen.main.news.model

sealed class NewsSideEffect{
    data class ShowSnackbar(val text: String): NewsSideEffect()
}
