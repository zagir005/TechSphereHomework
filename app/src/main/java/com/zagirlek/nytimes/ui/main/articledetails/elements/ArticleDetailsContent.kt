package com.zagirlek.nytimes.ui.main.articledetails.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.core.ui.elements.ArticleStatus
import com.zagirlek.nytimes.core.ui.elements.ArticleText
import com.zagirlek.nytimes.core.ui.elements.CategoryDateInfo
import com.zagirlek.nytimes.core.utils.timeAgoOrDate
import com.zagirlek.nytimes.domain.model.ArticleFullWithStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailsContent(
    article: ArticleFullWithStatus,
    modifier: Modifier = Modifier,
    toggleFavoriteStatus: (String) -> Unit = {},
    toggleReadStatus: (String) -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(horizontal = 8.dp)
    ){
        CategoryDateInfo(
            category = article.category ?: NewsCategory.OTHER,
            date = article.pubDate.timeAgoOrDate()
        )

        HorizontalDivider()

        ArticleText(
            title = article.title,
            text = article.fullText,
            imageUrl = article.imageUrl ?: "",
            author = article.creator?.ifEmpty { article.sourceName } ?: article.sourceName,
        )

        HorizontalDivider()

        ArticleStatus(
            isRead = article.isRead,
            isFavorite = article.isFavorite,
            onFavoriteClick = { toggleFavoriteStatus(article.articleId) },
            onReadClick = { toggleReadStatus(article.articleId) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    }
}