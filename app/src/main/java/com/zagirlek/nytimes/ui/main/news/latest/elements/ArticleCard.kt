package com.zagirlek.nytimes.ui.main.news.latest.elements

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zagirlek.nytimes.R
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.core.ui.elements.ArticleStatus
import com.zagirlek.nytimes.core.ui.elements.ArticleText
import com.zagirlek.nytimes.core.ui.elements.CategoryDateInfo
import com.zagirlek.nytimes.core.ui.elements.NyTimesPreview
import com.zagirlek.nytimes.ui.main.news.model.Article

@Composable
fun ArticleCard(
    article: Article,
    modifier: Modifier = Modifier,
    onClick: (Article) -> Unit = {},
    onReadToggle: (Article) -> Unit = {},
    onFavoriteToggle: (Article) -> Unit = {},
) {
    val swipeState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            when (value) {
                SwipeToDismissBoxValue.EndToStart -> {
                    onFavoriteToggle(article)
                    false
                }
                SwipeToDismissBoxValue.StartToEnd -> {
                    onReadToggle(article)
                    false
                }
                else -> true
            }
        }
    )
    SwipeToDismissBox(
        state = swipeState,
        backgroundContent = {
            if (swipeState.dismissDirection == SwipeToDismissBoxValue.EndToStart){
                Icon(
                    painter = painterResource(
                        id = if (article.isFavorite)
                            R.drawable.ic_favorite
                        else
                            R.drawable.ic_favorite_filled
                    ),
                    contentDescription = stringResource(R.string.toggle_favorite_status),
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Red, shape = RoundedCornerShape(12.dp))
                        .wrapContentSize(Alignment.CenterEnd)
                        .padding(8.dp)
                )
            }else if(swipeState.dismissDirection == SwipeToDismissBoxValue.StartToEnd){
                Icon(
                    painter = painterResource(
                        id = if(article.isRead)
                            R.drawable.ic_mark_article_unread
                        else
                            R.drawable.ic_mark_article_read
                    ),
                    contentDescription = stringResource(R.string.toggle_read_status),
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Blue, shape = RoundedCornerShape(12.dp))
                        .wrapContentSize(Alignment.CenterStart)
                        .padding(8.dp)
                )
            }
        }
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            onClick = { onClick(article) },
            modifier = modifier
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CategoryDateInfo(
                    category = article.category ?: NewsCategory.OTHER,
                    date = article.pubDate
                )

                HorizontalDivider()

                ArticleText(
                    title = article.title,
                    text = article.description
                        ?: stringResource(R.string.article_dont_have_description),
                    imageUrl = article.imageUrl ?: "",
                    author = article.creator,
                    textMaxLines = 3
                )

                HorizontalDivider()

                ArticleStatus(
                    isRead = article.isRead,
                    isFavorite = article.isFavorite,
                    onFavoriteClick = { onFavoriteToggle(article) },
                    onReadClick = { onReadToggle(article) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Preview(
    name = "Default"
)
@Preview(
    name = "Night mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun ArticleCardPreview() {
    NyTimesPreview {
        ArticleCard(
            article = Article.getExampleArticle()
        )
    }
}