package me.ibrahim.bonialbrochures.presentation.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
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

    if (brochures.loadState.refresh is LoadState.Loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    if (brochures.loadState.refresh is LoadState.Error) {
        val error = (brochures.loadState.refresh as LoadState.Error).error
        ErrorScreen(message = error.localizedMessage ?: "Something went wrong") {
            brochures.retry()
        }
        return
    }


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

        if (brochures.loadState.append is LoadState.Loading) {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }

        if (brochures.loadState.append is LoadState.Error) {
            item {
                val error = (brochures.loadState.append as LoadState.Error).error
                RetryItem(error.localizedMessage ?: "Failed to load more data") {
                    brochures.retry()
                }
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

@Composable
fun ErrorScreen(message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = message, color = Color.Red,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}

@Composable
fun RetryItem(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, color = Color.Red)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}