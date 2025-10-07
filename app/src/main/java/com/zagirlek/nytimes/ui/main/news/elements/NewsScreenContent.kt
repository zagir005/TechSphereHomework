package com.zagirlek.nytimes.ui.main.news.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.valentinilk.shimmer.shimmer
import com.zagirlek.nytimes.R
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.core.ui.elements.AppTextField
import com.zagirlek.nytimes.core.ui.model.Article
import com.zagirlek.nytimes.ui.elements.NewsCategorySelector
import com.zagirlek.nytimes.ui.main.news.model.NewsModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreenContent(
    model: NewsModel,
    modifier: Modifier = Modifier,
    showError: (String) -> Unit,
    searchValueChanged: (String?) -> Unit = {},
    selectedCategoryChanged: (NewsCategory?) -> Unit = {},
    onArticleClick: (Article) -> Unit = {},
    onReadToggle: (articleId: String) -> Unit = {},
    onFavoriteToggle: (articleId: String) -> Unit = {}
) {
    val newsList = model.newsPages.collectAsLazyPagingItems()

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

            NewsList(
                articlesList = newsList,
                modifier = Modifier.fillMaxHeight()
            ){ article, modifier ->
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

@Composable
private fun NewsList (
    articlesList: LazyPagingItems<Article>,
    modifier: Modifier = Modifier,
    articleCard: @Composable (article: Article, modifier: Modifier) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        when(articlesList.loadState.refresh){
            is LoadState.Error -> {
                item {
                    ErrorRetryAlert(
                        onRetry = { articlesList.retry() }
                    )
                }
            }
            LoadState.Loading -> {
                items(5) {
                    ArticleCard(
                        article = Article(),
                        modifier = Modifier.shimmer()
                    )
                }
            }
            else -> {
                items(
                    count = articlesList.itemCount,
                    key = articlesList.itemKey { it.articleId }
                ){
                    val article = articlesList[it]
                    if (article != null)
                        articleCard(article, Modifier.animateItem())
                }
                if (articlesList.loadState.append is LoadState.Loading)
                    item { NewsListLoadingIndicator() }
                if (articlesList.loadState.append is LoadState.Error)
                    item { ErrorRetryAlert { articlesList.retry() } }
            }
        }
    }
}

@Composable
fun NewsListLoadingIndicator() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            strokeWidth = 2.dp,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Загружаем новости...",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ErrorRetryAlert(
    modifier: Modifier = Modifier,
    message: String = "Отсутствует интернет-соединие",
    onRetry: () -> Unit = {  }
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message)
        Button(
            onClick = {
                onRetry()
            }
        ) {
            Text(text = "Попытаться снова")
        }
    }
}
