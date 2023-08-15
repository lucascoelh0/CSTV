package com.luminay.gomatches.features.matches.ui.matchdetails.players

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.domain.models.PlayerModel
import com.luminay.gomatches.utils.getPlayerModelMock
import com.luminay.gomatches.theme.Blue80

@Composable
fun PlayerNameAndNickname(
    playerModel: PlayerModel,
    isFirstTeam: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = if (isFirstTeam) Alignment.End else Alignment.Start,
    ) {
        Text(
            text = playerModel.name,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.W700,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Text(
            text = "${playerModel.firstName} ${playerModel.lastName}",
            color = Blue80,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
fun PlayerNameAndNicknameFirstTeamPreview() {
    PlayerNameAndNickname(
        playerModel = getPlayerModelMock(),
        isFirstTeam = true,
    )
}

@Preview
@Composable
fun PlayerNameAndNicknameSecondTeamPreview() {
    PlayerNameAndNickname(
        playerModel = getPlayerModelMock(),
        isFirstTeam = false,
    )
}
