package me.ibrahim.bonialbrochures.presentation.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import me.ibrahim.bonialbrochures.presentation.MainViewModel

@Composable
fun BrochuresScreen(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel<MainViewModel>()
) {

    val brochures = viewModel.brochures.collectAsLazyPagingItems()

    val columns = rememberColumnCount()

    when (val refreshState = brochures.loadState.refresh) {
        is LoadState.Loading -> FullScreenLoader()
        is LoadState.Error -> ErrorScreen(message = refreshState.error.localizedMessage ?: "Something went wrong") {
            brochures.retry()
        }

        else -> BrochuresGrid(brochures, columns, contentPadding, modifier)
    }
}

@Composable
fun rememberColumnCount(): Int {
    val configuration = LocalConfiguration.current
    return remember(configuration) {
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2
    }
}