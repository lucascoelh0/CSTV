package com.lucascoelho.cstv.features.matches.ui.matchlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lucascoelho.domain.models.MatchModel
import com.lucascoelho.cstv.utils.getMatchModelMock
import com.lucascoelho.cstv.features.matches.ui.common.TeamVsTeam
import com.lucascoelho.cstv.features.matches.ui.common.TimeLabel
import com.lucascoelho.cstv.theme.Gray30
import com.lucascoelho.cstv.theme.Purple80

@Composable
fun MatchInfoCard(
    matchModel: MatchModel,
    onClick: (MatchModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = Purple80,
                shape = RoundedCornerShape(16.dp),
            )
            .clickable {
                onClick(matchModel)
            },
    ) {
        Column {
            TimeLabel(
                text = matchModel.beginAt,
                modifier = Modifier
                    .align(alignment = Alignment.End),
                matchStatus = matchModel.status,
            )

            TeamVsTeam(
                matchModel = matchModel,
                modifier = Modifier
                    .padding(
                        vertical = 12.dp,
                    )
                    .align(alignment = Alignment.CenterHorizontally),
            )

            Divider(
                thickness = 1.dp,
                color = Gray30,
            )

            ImageWithLeagueName(
                leagueModel = matchModel.league,
                modifier = Modifier.padding(
                    top = 8.dp,
                    bottom = 8.dp,
                    start = 16.dp,
                )
            )
        }
    }
}

@Preview
@Composable
private fun MatchInfoCardPreview() {
    MatchInfoCard(
        matchModel = getMatchModelMock(),
        onClick = {},
        modifier = Modifier,
    )
}
