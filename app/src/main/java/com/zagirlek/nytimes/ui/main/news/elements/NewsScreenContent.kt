package com.zagirlek.nytimes.ui.main.news.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.zagirlek.nytimes.R
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.core.ui.elements.AppTextField
import com.zagirlek.nytimes.core.ui.model.Article
import com.zagirlek.nytimes.ui.elements.NewsCategorySelector
import com.zagirlek.nytimes.ui.main.news.model.NewsModel

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

    val refreshError = newsList.loadState.refresh as? LoadState.Error
    val appendError = newsList.loadState.append as? LoadState.Error

//    refreshError?.let {
//        when(val err = it.error){
//            is ServerError -> showError(err.message.toString())
//            is NetworkError -> showError(err.message.toString())
//            else -> showError("Какая-то ошибка: ${it.error.message}")
//        }
//    }
//    appendError?.let {
//        when(val err = it.error){
//            is ServerError -> showError(err.message.toString())
//            is NetworkError -> showError(err.message.toString())
//            else -> showError("Какая-то ошибка: ${it.error.message}")
//        }
//    }
//
//    refreshError?.let {
//        Log.d("PAGINGERROR", refreshError.error::class.toString())
//        Log.d("PAGINGERROR", refreshError.error.message.toString())
//    }
//
//    appendError?.let {
//        Log.d("PAGINGERROR", appendError.error::class.toString())
//        Log.d("PAGINGERROR", appendError.error.message.toString())
//    }

    Column(
        modifier = modifier
    ) {
        AppTextField(
            value = model.searchFieldValue ?: "",
            onValueChange = {
                searchValueChanged(it.ifEmpty { null })
                newsList.refresh() },
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
                newsList.refresh()
            }
        )

        Spacer(modifier = Modifier.height(2.dp))

        NewsList(
            articlesList = newsList,
            lastItem = {
                if (appendError != null)
                    ErrorRetryAlert {
                        newsList.refresh()
                    }
            }
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

@Composable
private fun NewsList (
    articlesList: LazyPagingItems<Article>,
    modifier: Modifier = Modifier,
    lastItem: @Composable () -> Unit = {},
    articleCard: @Composable (article: Article, modifier: Modifier) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            count = articlesList.itemCount,
            key = articlesList.itemKey { it.articleId }
        ){
            val article = articlesList[it]
            if (article != null)
                articleCard(article, Modifier.animateItem())
        }
        item { lastItem() }
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
