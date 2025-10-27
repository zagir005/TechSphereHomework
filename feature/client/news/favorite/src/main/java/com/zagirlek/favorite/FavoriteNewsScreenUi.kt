package com.zagirlek.favorite

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.zagirlek.articledetails.ArticleDetailsBottomSheet
import com.zagirlek.favorite.elements.FavoriteNewsScreenContent
import com.zagirlek.ui.elements.newscategory.toCategory

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
        modifier = Modifier
            .padding(horizontal = 4.dp),
        searchValueChanged = { component.searchByTitle(it) },
        selectedCategoryChanged = { component.filterByCategory(it?.toCategory()) },
        onFavoriteToggle = { component.toggleArticleFavoriteStatus(it) },
        onArticleClick = { component.showArticleDetails(it) }
    )
}