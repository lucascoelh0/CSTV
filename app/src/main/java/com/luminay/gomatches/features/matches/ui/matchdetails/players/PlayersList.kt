package com.luminay.gomatches.features.matches.ui.matchdetails.players

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.domain.models.PlayerModel

@Composable
fun PlayersList(
    players: List<PlayerModel>,
    isFirstTeam: Boolean,
) {
    players.forEach { player ->
        PlayerDetailsContainer(
            playerModel = player,
            isFirstTeam = isFirstTeam,
        )

        Spacer(modifier = Modifier.height(12.dp))
    }
}
