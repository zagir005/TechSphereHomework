
package com.zagirlek.articledetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.zagirlek.articledetails.elements.ArticleDetailsContent

@Composable
fun ArticleDetailsBottomSheet(
    articleComponent: ArticleDetailsComponent
) {
    val article by articleComponent.article.collectAsState()

    ArticleDetailsContent(
        state = article,
        toggleFavoriteStatus = { articleComponent.toggleFavoriteStatus() },
        toggleReadStatus = { articleComponent.toggleReadStatus() },
        retry = { articleComponent.retry() }
    )
}