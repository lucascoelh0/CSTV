package com.lucascoelho.cstv.common.ui.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.lucascoelho.cstv.R
import com.lucascoelho.cstv.utils.getTeamMock

@Composable
fun RoundImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    val imageRequestBuilder = ImageRequest.Builder(LocalContext.current)
        .crossfade(true)
        .data(imageUrl)
        .diskCacheKey(imageUrl)
        .networkCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .build()

    AsyncImage(
        modifier = modifier,
        model = if (imageUrl.isNotEmpty()) imageRequestBuilder else null,
        contentDescription = stringResource(id = R.string.image_label),
        placeholder = painterResource(id = R.drawable.ic_team_placeholder),
        error = painterResource(id = R.drawable.ic_team_placeholder),
        contentScale = ContentScale.Fit,
    )
}

@Preview
@Composable
private fun RoundImagePreview() {
    RoundImage(
        imageUrl = getTeamMock().imageUrl,
    )
}
