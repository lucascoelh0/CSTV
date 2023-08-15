package com.luminay.gomatches.features.matches.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.models.MatchModel
import com.example.domain.models.getOpponentsInfo
import com.luminay.gomatches.R
import com.luminay.gomatches.utils.getMatchModelMock
import com.luminay.gomatches.theme.White50

@Composable
fun TeamVsTeam(
    matchModel: MatchModel,
    modifier: Modifier = Modifier,
) {
    val opponentsPair by remember {
        mutableStateOf(
            matchModel.opponents.getOpponentsInfo()
        )
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TeamImageWithName(
            teamModel = opponentsPair.first,
        )

        Text(
            text = stringResource(id = R.string.vs),
            modifier = Modifier.padding(bottom = 10.dp),
            color = White50,
            fontSize = 12.sp,
        )

        TeamImageWithName(
            teamModel = opponentsPair.second,
        )
    }
}

@Preview
@Composable
private fun TeamVsTeamPreview() {
    TeamVsTeam(
        matchModel = getMatchModelMock(),
    )
}
