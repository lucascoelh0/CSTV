package com.lucascoelho.cstv.features.matches.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lucascoelho.cstv.common.ui.images.RoundImage
import com.lucascoelho.cstv.utils.getTeamMock
import com.lucascoelho.domain.models.TeamModel

@Composable
fun TeamImageWithName(
    teamModel: TeamModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        RoundImage(
            imageUrl = teamModel.imageUrl,
            modifier = Modifier.size(60.dp),
        )

        Text(
            text = teamModel.name,
            modifier.padding(top = 10.dp),
            fontSize = 10.sp,
            color = Color.White,
        )
    }
}

@Preview
@Composable
private fun TeamImageWithNamePreview() {
    TeamImageWithName(
        teamModel = getTeamMock(),
    )
}
