package com.zagirlek.nytimes.core.ui.elements

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zagirlek.nytimes.R

@Composable
fun ArticleStatus(
    isRead: Boolean,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    onFavoriteClick: () -> Unit = {},
    onReadClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
    ) {
        FilledIconButton(
            onClick = { onReadClick() },
            shape = RoundedCornerShape(6.dp)
        ) {
            Icon(
                painter = painterResource(
                    id = if (isRead) R.drawable.ic_mark_article_read
                    else R.drawable.ic_mark_article_unread
                ),
                contentDescription = stringResource(R.string.readstatus),
            )
        }

        FilledIconButton(
            onClick = onFavoriteClick,
            shape = RoundedCornerShape(6.dp)
        ) {
            Icon(
                painter = painterResource(
                    id = if (isFavorite) R.drawable.ic_favorite_filled
                    else R.drawable.ic_favorite
                ),
                contentDescription = stringResource(R.string.readstatus)
            )
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
private fun ArticleStatusPreview() {
    NyTimesPreview {
        ArticleStatus(true,true)
    }
}