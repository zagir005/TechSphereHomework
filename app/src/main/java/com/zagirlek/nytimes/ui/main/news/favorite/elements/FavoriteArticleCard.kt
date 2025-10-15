package com.zagirlek.nytimes.ui.main.news.favorite.elements

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.zagirlek.nytimes.R
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.core.ui.elements.AppChip
import com.zagirlek.nytimes.core.ui.elements.NyTimesPreview
import com.zagirlek.nytimes.ui.main.news.favorite.model.FavoriteArticle

@Composable
fun FavoriteArticleCard(
    article: FavoriteArticle,
    modifier: Modifier = Modifier,
    onClick: (FavoriteArticle) -> Unit = {},
    onFavoriteToggle: (FavoriteArticle) -> Unit = {},
) {

    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart){
                onFavoriteToggle(article)
                true
            } else false
        }
    )

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        enableDismissFromStartToEnd = false,
        modifier = modifier.fillMaxWidth(),
        backgroundContent = {
            if (swipeToDismissBoxState.dismissDirection == SwipeToDismissBoxValue.EndToStart){
                Icon(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = stringResource(R.string.toggle_favorite_status),
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Red, shape = RoundedCornerShape(8.dp))
                        .wrapContentSize(Alignment.CenterEnd)
                        .padding(8.dp)
                )
            }
        }
    ) {

        OutlinedCard(
            modifier = modifier
                .fillMaxWidth(),
            onClick = { onClick(article) }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
            ) {
                AsyncImage(
                    model = article.imageUrl,
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.nytimes_logo),
                    modifier = Modifier
                        .size(85.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop,
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    AppChip(
                        text = stringResource(article.category?.resId ?: NewsCategory.OTHER.resId)
                    )
                }

                IconButton(
                    onClick = { onFavoriteToggle(article) },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (article.isFavorite) R.drawable.ic_favorite_filled
                            else R.drawable.ic_favorite
                        ),
                        contentDescription = stringResource(R.string.readstatus)
                    )
                }
            }
        }
    }
}

@Preview
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun FavoriteArticleCardPreview() {
    NyTimesPreview {
        FavoriteArticleCard(
            article = FavoriteArticle(
                articleId = "",
                title = "Lorem ipsum",
                imageUrl = "",
                category = NewsCategory.OTHER,
                isFavorite = true
            )
        )
    }
}