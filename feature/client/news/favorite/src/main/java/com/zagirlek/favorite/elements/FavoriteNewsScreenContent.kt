package com.zagirlek.favorite.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zagirlek.favorite.model.FavoriteNewsModel
import com.zagirlek.ui.R
import com.zagirlek.ui.elements.textfield.AppTextField
import com.zagirlek.ui.elements.NewsCategorySelector
import com.zagirlek.ui.elements.NewsCategoryUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FavoriteNewsScreenContent(
    model: FavoriteNewsModel,
    modifier: Modifier = Modifier,
    searchValueChanged: (String?) -> Unit = {},
    selectedCategoryChanged: (NewsCategoryUi?) -> Unit = {},
    onArticleClick: (articleId: String) -> Unit = {},
    onFavoriteToggle: (articleId: String) -> Unit = {}
) {
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


        LazyColumn {
            items(
                items = model.newsList,
                key = { it.articleId }
            ){ article ->
                FavoriteArticleCard(
                    article = article,
                    modifier = Modifier.animateItem(),
                    onClick = { onArticleClick(article.articleId) },
                    onFavoriteToggle = { onFavoriteToggle(article.articleId) }
                )
            }
        }

    }
}