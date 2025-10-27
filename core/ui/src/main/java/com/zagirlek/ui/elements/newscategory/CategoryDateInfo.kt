package com.zagirlek.ui.elements.newscategory

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zagirlek.ui.R
import com.zagirlek.ui.elements.AppChip

@Composable
fun CategoryDateInfo(category: NewsCategoryUi, date: String) {
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

@Preview
@Composable
private fun CategoryDateInfoPreview() {
    CategoryDateInfo(
        category = NewsCategoryUi.ENTERTAINMENT,
        date = "05.05.05"
    )
}