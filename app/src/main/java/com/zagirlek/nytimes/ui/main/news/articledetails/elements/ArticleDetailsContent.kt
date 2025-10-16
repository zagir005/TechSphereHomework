package com.zagirlek.nytimes.ui.main.news.articledetails.elements

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import com.zagirlek.nytimes.R
import com.zagirlek.nytimes.core.ui.elements.AppButton
import com.zagirlek.nytimes.core.ui.elements.ArticleStatus
import com.zagirlek.nytimes.core.ui.elements.ArticleText
import com.zagirlek.nytimes.core.ui.elements.CategoryDateInfo
import com.zagirlek.nytimes.core.ui.elements.NyTimesPreview
import com.zagirlek.nytimes.core.utils.timeAgoOrDate
import com.zagirlek.nytimes.domain.model.ArticleFullWithStatus
import com.zagirlek.nytimes.ui.main.news.articledetails.store.ArticleDetailsStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailsContent(
    state: ArticleDetailsStore.State,
    modifier: Modifier = Modifier,
    retry: () -> Unit,
    toggleFavoriteStatus: () -> Unit,
    toggleReadStatus: () -> Unit
) {
    when{
        state.loading -> {
            Loading()
        }
        state.article != null -> {
            Content(
                article = state.article,
                toggleReadStatus = toggleReadStatus,
                toggleFavoriteStatus = toggleFavoriteStatus,
                modifier = modifier
            )
        }
        state.errorMsgRes != null -> {
            Error(
                errorMsg = stringResource(state.errorMsgRes),
                retry = retry
            )
        }
    }
}

@Composable
private fun Error(
    errorMsg: String,
    modifier: Modifier = Modifier,
    retry: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(vertical = 20.dp)
            .fillMaxWidth()
    ){
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.error),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = errorMsg,
                style = MaterialTheme.typography.bodyLarge
            )
            AppButton(
                onClick = retry
            ){
                Text(
                    text = stringResource(R.string.repeat)
                )
            }
        }
    }
}

@Composable
private fun Loading() {
    Content(
        article = ArticleFullWithStatus(),
        modifier = Modifier.shimmer()
    )
}

@Composable
private fun Content(
    article: ArticleFullWithStatus,
    modifier: Modifier = Modifier,
    toggleFavoriteStatus: () -> Unit = {},
    toggleReadStatus: () -> Unit = {},
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .verticalScroll(scrollState)
    ){
        CategoryDateInfo(
            category = article.category,
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
            onFavoriteClick = toggleFavoriteStatus,
            onReadClick = toggleReadStatus,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
@Composable
private fun ContentPreview() {
    NyTimesPreview {
        Content(
            ArticleFullWithStatus(
                fullText = "Lorem ipsum ".repeat(20),
                title = "Lorem title"
            )
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
@Composable
private fun ErrorPreview() {
    NyTimesPreview {
        Error(
            errorMsg = "Отсутствует интернет соединение"
        ) { }
    }
}