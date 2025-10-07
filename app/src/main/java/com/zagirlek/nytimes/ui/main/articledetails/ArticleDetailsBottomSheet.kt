package com.zagirlek.nytimes.ui.main.articledetails

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.valentinilk.shimmer.shimmer
import com.zagirlek.nytimes.domain.model.ArticleFullWithStatus
import com.zagirlek.nytimes.ui.main.articledetails.elements.ArticleDetailsContent

@Composable
fun ArticleDetailsBottomSheet(
    articleComponent: ArticleDetailsComponent
) {
    val article by articleComponent.article.collectAsState()
    val scrollState = rememberScrollState()
    ArticleDetailsContent(
        article = article ?: ArticleFullWithStatus(),
        modifier = if
                (article == null)
            Modifier.shimmer()
        else
            Modifier.verticalScroll(scrollState),
        toggleFavoriteStatus = { articleComponent.toggleFavoriteStatus() },
        toggleReadStatus = { articleComponent.toggleReadStatus() }
    )
}