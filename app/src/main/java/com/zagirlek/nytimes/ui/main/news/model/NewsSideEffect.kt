package com.zagirlek.nytimes.ui.main.news.model

sealed class NewsSideEffect{
    data class ShowSnackbar(val text: String): NewsSideEffect()
}
