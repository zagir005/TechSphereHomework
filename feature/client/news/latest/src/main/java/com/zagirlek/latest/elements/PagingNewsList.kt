package com.zagirlek.latest.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.valentinilk.shimmer.shimmer
import com.zagirlek.common.model.Article

@Composable
fun PagingNewsList (
    articlesList: LazyPagingItems<Article>,
    listState: LazyListState,
    modifier: Modifier = Modifier,
    articleCard: @Composable (article: Article, modifier: Modifier) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
        state = listState,
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
                    articleCard(
                        Article(),
                        Modifier.shimmer()
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
private fun NewsListLoadingIndicator() {
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
