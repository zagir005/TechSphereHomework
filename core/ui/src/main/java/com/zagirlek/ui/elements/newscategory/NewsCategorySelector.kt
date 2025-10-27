package com.zagirlek.ui.elements.newscategory

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zagirlek.ui.R
import com.zagirlek.ui.elements.NyTimesPreview


@Composable
fun NewsCategorySelector(
    selectedCategory: NewsCategoryUi?,
    onSelectedCategoryChange: (NewsCategoryUi?) -> Unit,
    modifier: Modifier = Modifier
) {
    val categoryList = mutableListOf<NewsCategoryUi?>(null) + NewsCategoryUi.entries

    LazyRow(
        modifier = modifier.fillMaxWidth()
    ) {
        items(categoryList){ category ->
            FilterChip(
                selected = category == selectedCategory,
                onClick = {
                    onSelectedCategoryChange(category)
                },
                label = {
                    Text(
                        text = stringResource(category?.resId ?: R.string.all)
                    )
                },
                modifier = Modifier.padding(2.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}


@Preview(
    name = "Default"
)
@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun NewsCategorySelectorPreview() {
    var selectedCategory by remember { mutableStateOf<NewsCategoryUi?>(null) }
    NyTimesPreview {
        NewsCategorySelector(
            selectedCategory = selectedCategory,
            onSelectedCategoryChange = { selectedCategory = it }
        )
    }
}