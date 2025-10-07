package com.zagirlek.nytimes.ui.main.news

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.zagirlek.nytimes.ui.main.articledetails.ArticleDetailsBottomSheet
import com.zagirlek.nytimes.ui.main.news.elements.NewsScreenContent
import com.zagirlek.nytimes.ui.main.news.model.NewsSideEffect


//favoriteListMode это костыль, делал в спешке, простите
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreenUi(
    component: NewsScreen,
    favoriteListMode: Boolean,
    showSnackbar: (String) -> Unit,
) {
    val context = LocalContext.current
    val model by component.model.collectAsState()
    val sideEffect = component.sideEffect

    LaunchedEffect(sideEffect) {
        sideEffect.collect { effect ->
            when(effect){
                is NewsSideEffect.ShowSnackbar -> showSnackbar(context.getString(effect.msgRes))
            }
        }

    }
    val childSlot by component.dialog.subscribeAsState()

    childSlot.child?.instance?.let { sheetComponent ->
        ModalBottomSheet(
            onDismissRequest = { component.hideArticleDetails() },
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ) {
            ArticleDetailsBottomSheet(sheetComponent)
        }
    }


    NewsScreenContent(
        model = model,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        favoriteListMode = favoriteListMode,
        searchValueChanged = { component.searchByTitle(it) },
        showError = showSnackbar,
        selectedCategoryChanged = { component.filterByCategory(it) },
        onArticleClick = { component.showArticleDetails(it.articleId) },
        onReadToggle = { component.toggleArticleReadStatus(it) },
        onFavoriteToggle = { component.toggleArticleFavoriteStatus(it) }
    )
}