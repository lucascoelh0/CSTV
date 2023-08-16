package com.lucascoelho.cstv.features.matches.ui.matchdetails.players

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lucascoelho.domain.models.PlayerModel

@Composable
fun PlayersTable(
    players: Pair<List<PlayerModel>, List<PlayerModel>>,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 22.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            Modifier.weight(1f),
        ) {
            PlayersList(
                players = players.first,
                isFirstTeam = true,
            )
        }

        Spacer(modifier = Modifier.width(13.dp))

        Column(
            modifier = Modifier
                .weight(1f),
        ) {
            PlayersList(
                players = players.second,
                isFirstTeam = false,
            )
        }
    }
}
