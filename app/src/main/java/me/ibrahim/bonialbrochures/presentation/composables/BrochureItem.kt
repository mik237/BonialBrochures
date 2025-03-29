package me.ibrahim.bonialbrochures.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import me.ibrahim.bonialbrochures.R
import me.ibrahim.bonialbrochures.domain.models.BrochureData

@Composable
fun BrochureItem(
    modifier: Modifier = Modifier,
    brochure: BrochureData
) {
    val context = LocalContext.current
    val placeholderImage = R.drawable.placeholder

    val imageRequest = ImageRequest.Builder(context)
        .crossfade(400)
        .dispatcher(Dispatchers.IO)
        .data(brochure.brochureImage)
        .memoryCacheKey(brochure.brochureImage)
        .diskCacheKey(brochure.brochureImage)
        .placeholder(placeholderImage)
        .error(placeholderImage)
        .fallback(placeholderImage)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .build()


    Card(
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(width = 1.dp, color = Color.Gray),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.8f),
                contentScale = ContentScale.Fit,
                model = imageRequest,
                contentDescription = null
            )

            Text(
                brochure.publisherName ?: brochure.publisherId,
                minLines = 2,
                maxLines = 2,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                ),
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
        }
    }


}