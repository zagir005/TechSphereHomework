package com.zagirlek.nytimes.ui.main.news.elements

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zagirlek.nytimes.R
import com.zagirlek.nytimes.core.model.NewsCategory
import com.zagirlek.nytimes.core.ui.elements.AppChip
import com.zagirlek.nytimes.core.ui.elements.NyTimesPreview
import com.zagirlek.nytimes.core.ui.model.Article

@Composable
fun ArticleCard(
    article: Article,
    modifier: Modifier = Modifier,
    onClick: (Article) -> Unit = {},
    onReadToggle: (Article) -> Unit = {},
    onFavoriteToggle: (Article) -> Unit = {},
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
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
                descriptionMaxLines = 3
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

@Composable
private fun CategoryDateInfo(category: NewsCategory, date: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppChip(
            text = stringResource(category.resId),
            modifier = Modifier.padding(2.dp)
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(R.drawable.ic_clock),
            contentDescription = null,
            modifier = Modifier.padding(2.dp)
        )
        Text(
            text = date,
            modifier = Modifier.padding(2.dp)
        )
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