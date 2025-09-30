package com.zagirlek.nytimes.ui.screen.main.news

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey

@Composable
fun NewsScreen(
    component: NewsComponent
) {
    val list = component.newsList.collectAsLazyPagingItems()

    val refreshError = list.loadState.refresh as? LoadState.Error
    val appendError = list.loadState.append as? LoadState.Error
    Log.d("APPENDERROR", appendError?.error?.message.toString())
    LazyColumn {
        items(
            count = list.itemCount,
            key = list.itemKey { it.articleId }
        ){
            val article = list[it]
            Text(
                text = article?.title.orEmpty(),
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}