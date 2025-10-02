package com.zagirlek.nytimes.ui.screen.main.news

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zagirlek.nytimes.ui.screen.main.news.elements.NewsScreenContent

@Composable
fun NewsScreenUi(
    component: NewsScreen,
    showSnackbar: (String) -> Unit
) {
    val model by component.model.collectAsState()

    NewsScreenContent(
        model = model,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
        searchValueChanged = { component.searchByTitle(it) },
        showError = showSnackbar,
        selectedCategoryChanged = { component.filterByCategory(it) },
        onArticleClick = {},
        onReadToggle = { component.toggleArticleReadStatus(it) },
        onFavoriteToggle = { component.toggleArticleFavoriteStatus(it) }
    )
}