package me.ibrahim.bonialbrochures.presentation.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import me.ibrahim.bonialbrochures.data.BrochureType
import me.ibrahim.bonialbrochures.domain.models.BrochureData

@Composable
fun BrochuresGrid(
    brochures: LazyPagingItems<BrochureData>,
    columns: Int,
    contentPadding: PaddingValues,
    modifier: Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        contentPadding = contentPadding
    ) {
        items(
            count = brochures.itemCount,
            key = { index -> brochures[index]?.id ?: index },
            span = { index ->
                if (brochures[index]?.contentType == BrochureType.BrochurePremium.type)
                    GridItemSpan(maxLineSpan)
                else GridItemSpan(1)
            }
        ) { index ->
            brochures[index]?.let { BrochureItem(brochure = it) }
        }

        when (val appendState = brochures.loadState.append) {
            is LoadState.Loading -> item { LoadingItem() }
            is LoadState.Error -> item {
                RetryItem(message = appendState.error.localizedMessage ?: "Failed to load more data") {
                    brochures.retry()
                }
            }

            else -> {}
        }
    }
}