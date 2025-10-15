package com.zagirlek.nytimes.ui.main.news.favorite

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.zagirlek.nytimes.ui.main.news.articledetails.ArticleDetailsBottomSheet
import com.zagirlek.nytimes.ui.main.news.favorite.elements.FavoriteNewsScreenContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteNewsScreenUi(
    component: FavoriteNewsScreen
) {
    val model by component.model.collectAsState()
    val childSlot by component.dialog.subscribeAsState()

    childSlot.child?.instance?.let { sheetComponent ->
        ModalBottomSheet(
            onDismissRequest = { component.hideArticleDetails() },
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ) {
            ArticleDetailsBottomSheet(sheetComponent)
        }
    }

    FavoriteNewsScreenContent(
        model = model,
        searchValueChanged = { component.searchByTitle(it) },
        selectedCategoryChanged = { component.filterByCategory(it) },
        onFavoriteToggle = { component.toggleArticleFavoriteStatus(it) },
        onArticleClick = { component.showArticleDetails(it) }
    )
}