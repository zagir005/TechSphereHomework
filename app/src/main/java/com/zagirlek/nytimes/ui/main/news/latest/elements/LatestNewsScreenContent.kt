package com.zagirlek.nytimes.ui.main.news.latest.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.zagirlek.nytimes.R
import com.zagirlek.nytimes.core.error.toNewsApiError
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.core.ui.elements.AppTextField
import com.zagirlek.nytimes.ui.elements.NewsCategorySelector
import com.zagirlek.nytimes.ui.main.news.latest.model.LatestNewsModel
import com.zagirlek.nytimes.ui.main.news.model.Article

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LatestNewsScreenContent(
    model: LatestNewsModel,
    modifier: Modifier = Modifier,
    showError: (String) -> Unit,
    searchValueChanged: (String?) -> Unit = {},
    selectedCategoryChanged: (NewsCategory?) -> Unit = {},
    onArticleClick: (Article) -> Unit = {},
    onReadToggle: (articleId: String) -> Unit = {},
    onFavoriteToggle: (articleId: String) -> Unit = {}
) {
    val newsList = model.newsPages.collectAsLazyPagingItems()
    val lazyListState = rememberLazyListState()
    val refreshError = newsList.loadState.refresh as? LoadState.Error
    val appendError = newsList.loadState.refresh as? LoadState.Error

    refreshError?.let {
        showError(refreshError.error.toNewsApiError().message ?: "Неизвестная ошибка")
    }
    appendError?.let {
        showError(appendError.error.toNewsApiError().message ?: "Неизвестная ошибка")
    }

    PullToRefreshBox(
        isRefreshing = newsList.loadState.refresh is LoadState.Loading,
        onRefresh = { newsList.refresh() },
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = modifier
                .fillMaxHeight()
        ) {
            AppTextField(
                value = model.searchFieldValue ?: "",
                onValueChange = { searchValueChanged(it.ifEmpty { null }) },
                label = stringResource(R.string.search),
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(2.dp))

            NewsCategorySelector(
                selectedCategory = model.selectedCategory,
                onSelectedCategoryChange = {
                    selectedCategoryChanged(it)

                }
            )

            Spacer(modifier = Modifier.height(2.dp))

            PagingNewsList(
                articlesList = newsList,
                listState = lazyListState,
                modifier = Modifier.fillMaxHeight()
            ) { article, modifier ->
                ArticleCard(
                    article = article,
                    onClick = {
                        onArticleClick(article)
                    },
                    modifier = modifier,
                    onFavoriteToggle = { onFavoriteToggle(it.articleId) },
                    onReadToggle = { onReadToggle(it.articleId) }
                )
            }
        }
    }
}