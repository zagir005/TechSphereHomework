package com.zagirlek.latest.model

sealed class NewsSideEffect{
    data class ShowSnackbar(val msgRes: Int): NewsSideEffect()
}
