package com.zagirlek.nytimes.core.ui.elements

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.zagirlek.nytimes.R

@Composable
fun ArticleText(
    title: String,
    text: String,
    imageUrl: String,
    author: String,
    modifier: Modifier = Modifier,
    textMaxLines: Int = Int.MAX_VALUE,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row {
            Text(
                text = author,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier.width(22.dp))
            Text(
                text = "Время чтения: 5 мин",
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
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
            maxLines = textMaxLines
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
            author = "Zagirfdsakfjdskafjsda;ffsdafksd;ajf",
            textMaxLines = 3,
        )
    }
}