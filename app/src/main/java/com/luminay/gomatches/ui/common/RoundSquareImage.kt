package com.luminay.gomatches.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.luminay.gomatches.R
import com.luminay.gomatches.common.getTeamMock

@Composable
fun RoundSquareImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    Box {
        val imageRequestBuilder = ImageRequest.Builder(LocalContext.current)
            .crossfade(true)
            .data(imageUrl)
            .diskCacheKey(imageUrl)
            .networkCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build()

        AsyncImage(
            modifier = modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp)),
            model = if (imageUrl.isNotEmpty()) imageRequestBuilder else null,
            contentDescription = stringResource(id = R.string.image_label),
            placeholder = painterResource(id = R.drawable.ic_round_square),
            error = painterResource(id = R.drawable.ic_round_square),
            contentScale = ContentScale.FillBounds,
        )
    }
}

@Preview
@Composable
private fun RoundSquareImagePreview() {
    RoundSquareImage(
        imageUrl = getTeamMock().imageUrl,
        modifier = Modifier.size(48.dp)
    )
}
