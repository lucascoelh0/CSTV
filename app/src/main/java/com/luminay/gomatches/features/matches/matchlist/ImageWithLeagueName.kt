package com.luminay.gomatches.features.matches.matchlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.models.LeagueModel
import com.luminay.gomatches.common.getLeagueMock
import com.luminay.gomatches.ui.common.RoundImage

@Composable
fun ImageWithLeagueName(
    leagueModel: LeagueModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        RoundImage(
            imageUrl = leagueModel.imageUrl,
            modifier = Modifier.size(16.dp),
        )

        Text(
            text = leagueModel.name,
            fontSize = 8.sp,
            color = Color.White,
        )
    }
}

@Preview
@Composable
fun PreviewImageWithLeagueName() {
    ImageWithLeagueName(
        leagueModel = getLeagueMock(),
    )
}
