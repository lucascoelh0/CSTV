package com.luminay.gomatches.features.matches.matchdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.models.Resource
import com.example.core.models.Status
import com.example.domain.models.MatchModel
import com.example.domain.models.PlayerModel
import com.example.domain.models.TeamDetailsModel
import com.luminay.gomatches.R
import com.luminay.gomatches.common.getMatchModelMock
import com.luminay.gomatches.theme.Purple80
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun MatchDetailsScreen(
    id: Int,
    matchModel: MatchModel,
) {
    Scaffold(
        topBar = {
            TopBar(
                matchModel = matchModel,
            )
        },
        content = { padding ->
            MatchDetails(
                paddingValues = padding,
                matchModel = matchModel,
            )
        }
    )
}

@Composable
private fun TopBar(
    matchModel: MatchModel,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(id = R.string.back),
            modifier = Modifier
                .size(24.dp),
            tint = Color.White,
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "${matchModel.league.name} - ${matchModel.serie.name}",
            color = Color.White,
            fontSize = 18.sp,
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}



@Composable
fun MatchStatus(
    paddingValues: PaddingValues,
    matchDetails: Resource<List<TeamDetailsModel>>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        when (matchDetails?.status) {
            Status.LOADING -> {
                CircularProgressIndicator(
                    color = Color.White,
                )
            }

            Status.SUCCESS -> {
                matchDetails.data?.let {
                    MatchDetails(
                        matchDetails = it,
                    )
                } ?: run {
                    ErrorMessage(
                        onRetry = onRetry,
                    )
                }
            }

            else -> {
                ErrorMessage(
                    onRetry = onRetry,
                )
            }
        }
    }
}

@Composable
fun MatchDetails(
    matchModel: MatchModel,
    matchDetailsViewModel: MatchDetailsViewModel = hiltViewModel(),
) {
    val teams by matchDetailsViewModel.teams.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(matchModel) {
        matchDetailsViewModel.fetchTeamsDetails(matchModel)
    }


}

@Composable
fun TeamPlayersList(
    players: List<PlayerModel>,
    isFirstTeam: Boolean,
) {
    players.forEach { player ->
        PlayerDetailsContainer(
            playerModel = player,
            isFirstTeam = isFirstTeam,
        )
    }
}

@Composable
fun ErrorMessage(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(id = R.string.loading_error),
            color = Color.White,
        )

        Button(
            onClick = onRetry,
            modifier = Modifier.padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple80,
            ),
        ) {
            Text(
                text = stringResource(id = R.string.try_again),
            )
        }
    }
}

@Preview
@Composable
private fun TopBarPreview() {
    TopBar(
        matchModel = getMatchModelMock(),
    )
}
