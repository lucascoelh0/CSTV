package com.luminay.gomatches.ui.common

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
import com.luminay.gomatches.R
import com.luminay.gomatches.common.getTeamMock

@Composable
fun RoundImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .crossfade(true)
            .data(imageUrl)
            .diskCacheKey(imageUrl)
            .networkCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build(),
        contentDescription = stringResource(id = R.string.image_label),
        modifier = modifier,
        placeholder = painterResource(id = R.drawable.ic_team_placeholder),
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
