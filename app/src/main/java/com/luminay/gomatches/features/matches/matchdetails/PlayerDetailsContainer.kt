package com.luminay.gomatches.features.matches.matchdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.models.PlayerModel
import com.luminay.gomatches.common.getPlayerModelMock
import com.luminay.gomatches.theme.Purple80
import com.luminay.gomatches.ui.common.RoundSquareImage

@Composable
fun PlayerDetailsContainer(
    playerModel: PlayerModel,
    isFirstTeam: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(top = 3.dp)
            .background(
                color = Purple80,
                shape = RoundedCornerShape(
                    topEnd = if (isFirstTeam) 12.dp else 0.dp,
                    bottomEnd = if (isFirstTeam) 12.dp else 0.dp,
                    bottomStart = if (isFirstTeam) 0.dp else 12.dp,
                    topStart = if (isFirstTeam) 0.dp else 12.dp,
                )
            ),
    ) {
        if (isFirstTeam) {
            PlayerDetailsContainerFirstTeam(
                playerModel = playerModel
            )
        } else {
            PlayerDetailsContainerSecondTeam(
                playerModel = playerModel
            )
        }
    }
}

@Composable
private fun PlayerDetailsContainerFirstTeam(
    playerModel: PlayerModel,
) {
    Row(
        modifier = Modifier.heightIn(min = 54.dp),
        horizontalArrangement = Arrangement.End,
    ) {
        PlayerNameAndNickname(
            playerModel = playerModel,
            isFirstTeam = true,
            modifier = Modifier
                .padding(
                    top = 15.dp,
                    end = 16.dp,
                    bottom = 8.dp,
                    start = 8.dp,
                )
                .weight(1f),
        )

        RoundSquareImage(
            imageUrl = playerModel.imageUrl,
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(y = (-3).dp)
                .size(48.dp),
        )
    }
}

@Composable
private fun PlayerDetailsContainerSecondTeam(
    playerModel: PlayerModel,
) {
    Row(
        modifier = Modifier.heightIn(min = 54.dp),
        horizontalArrangement = Arrangement.Start,
    ) {
        RoundSquareImage(
            imageUrl = playerModel.imageUrl,
            modifier = Modifier
                .padding(
                    end = 16.dp,
                    start = 12.dp,
                )
                .offset(y = (-3).dp)
                .size(48.dp),
        )

        PlayerNameAndNickname(
            playerModel = playerModel,
            isFirstTeam = false,
            modifier = Modifier
                .padding(
                    top = 15.dp,
                    end = 8.dp,
                    bottom = 8.dp,
                )
                .weight(1f),
        )
    }
}

@Preview
@Composable
fun PlayerDetailsContainerFirstTeamPreview() {
    PlayerDetailsContainer(
        playerModel = getPlayerModelMock(),
        isFirstTeam = true,
    )
}

@Preview
@Composable
fun PlayerDetailsContainerSecondTeamPreview() {
    PlayerDetailsContainer(
        playerModel = getPlayerModelMock(),
        isFirstTeam = false,
    )
}
