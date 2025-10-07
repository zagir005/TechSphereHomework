package com.zagirlek.nytimes.ui.main.news.elements

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.zagirlek.nytimes.R
import com.zagirlek.nytimes.core.ui.elements.NyTimesPreview

@Composable
fun ArticleText(
    title: String,
    text: String,
    imageUrl: String,
    descriptionMaxLines: Int = Int.MAX_VALUE,
    author: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row {
            Text(
                text = author,
                color = MaterialTheme.colorScheme.surfaceVariant
            )
            Spacer(modifier.weight(1f))
            Text(
                text = "Время чтения: 5 мин",
                color = MaterialTheme.colorScheme.surfaceVariant
            )
        }

        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            placeholder = painterResource(R.drawable.nytimes_logo),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop,
        )

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 2
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = descriptionMaxLines
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
private fun ArticleTextPreview() {
    NyTimesPreview {
        ArticleText(
            title = "Заголовок статьи",
            text = "Lorem ipsum ".repeat(30),
            imageUrl = "",
            3,
            "Zagir",

        )
    }
}