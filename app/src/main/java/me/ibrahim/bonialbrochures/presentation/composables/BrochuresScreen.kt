package me.ibrahim.bonialbrochures.presentation.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import me.ibrahim.bonialbrochures.data.BrochureType
import me.ibrahim.bonialbrochures.presentation.MainViewModel

@Composable
fun BrochuresScreen(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel<MainViewModel>()
) {

    val brochures = viewModel.brochures.collectAsLazyPagingItems()

    val columns = rememberColumnCount()

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        contentPadding = contentPadding
    ) {
        items(
            count = brochures.itemCount,
            key = { index ->
                brochures[index]?.id ?: index
            },
            span = { index ->
                if (brochures[index]?.contentType == BrochureType.BrochurePremium.type) GridItemSpan(maxLineSpan) else GridItemSpan(1)
            }
        ) { index ->
            brochures[index]?.let {
                BrochureItem(brochure = it)
            }
        }
    }
}

@Composable
fun rememberColumnCount(): Int {
    val configuration = LocalConfiguration.current
    return remember(configuration) {
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2
    }
}