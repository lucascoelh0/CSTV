package com.luminay.gomatches.features.matches.matchdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.models.Status
import com.example.core.utils.TimeUtils
import com.example.domain.models.MatchModel
import com.example.domain.models.PlayerModel
import com.luminay.gomatches.R
import com.luminay.gomatches.common.getMatchModelMock
import com.luminay.gomatches.common.getPlayerModelMock
import com.luminay.gomatches.features.matches.TeamVsTeam
import com.luminay.gomatches.theme.DarkBlue900
import com.luminay.gomatches.theme.Purple80
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun MatchDetailsScreen(
    id: Int,
    navigator: DestinationsNavigator,
    matchModel: MatchModel,
) {
    Column(
        modifier = Modifier
            .background(DarkBlue900)
            .fillMaxSize(),
    ) {
        TopBar(
            matchModel = matchModel,
            onBackPressed = {
                navigator.popBackStack()
            },
            modifier = Modifier
                .padding(
                    top = 52.dp,
                    end = 24.dp,
                    bottom = 24.dp,
                    start = 24.dp,
                )
        )

        MatchStatus(
            matchModel = matchModel,
        )
    }
}

@Composable
private fun TopBar(
    matchModel: MatchModel,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(id = R.string.back),
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    onBackPressed()
                },
            tint = Color.White,
        )

        Spacer(modifier = Modifier.weight(1f))

        var leagueSerieName = matchModel.league.name

        if (matchModel.serie.name.isNotEmpty()) {
            leagueSerieName += " - ${matchModel.serie.name}"
        }

        Text(
            text = leagueSerieName,
            color = Color.White,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun MatchStatus(
    matchModel: MatchModel,
    modifier: Modifier = Modifier,
    matchDetailsViewModel: MatchDetailsViewModel = hiltViewModel(),
) {
    val teams by matchDetailsViewModel.teams.collectAsStateWithLifecycle(initialValue = null)
    LaunchedEffect(matchModel) {
        matchDetailsViewModel.fetchTeamsDetails(matchModel)
    }

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = if (teams?.status == Status.LOADING || teams?.status == Status.ERROR) {
            Alignment.Center
        } else {
            Alignment.TopCenter
        }
    ) {
        when (teams?.status) {
            Status.LOADING -> {
                CircularProgressIndicator(
                    color = Color.White,
                )
            }

            Status.SUCCESS -> {
                teams?.data?.let {
                    MatchDetails(
                        matchModel = matchModel,
                        players = matchDetailsViewModel.getTeamPlayersPair(it),
                    )
                } ?: run {
                    ErrorMessage(
                        onRetry = {
                            matchDetailsViewModel.fetchTeamsDetails(matchModel)
                        },
                    )
                }
            }

            else -> {
                ErrorMessage(
                    onRetry = {
                        matchDetailsViewModel.fetchTeamsDetails(matchModel)
                    },
                )
            }
        }
    }
}

@Composable
fun MatchDetails(
    matchModel: MatchModel,
    players: Pair<List<PlayerModel>, List<PlayerModel>>,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        TeamVsTeam(
            matchModel = matchModel,
        )

        Text(
            text = TimeUtils.formatScheduledDate(matchModel.scheduledAt),
            modifier = Modifier
                .padding(top = 20.dp),
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.W700,
        )

        PlayersTableRow(
            players = players,
        )
    }
}

@Composable
fun PlayersTableRow(
    players: Pair<List<PlayerModel>, List<PlayerModel>>,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 22.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            Modifier.weight(1f),
        ) {
            TeamPlayersList(
                players = players.first,
                isFirstTeam = true,
            )
        }

        Spacer(modifier = Modifier.width(13.dp))

        Column(
            modifier = Modifier
                .weight(1f),
        ) {
            TeamPlayersList(
                players = players.second,
                isFirstTeam = false,
            )
        }
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

        Spacer(modifier = Modifier.height(12.dp))
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
            text = stringResource(id = R.string.loading_match_details_error),
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
        onBackPressed = {},
    )
}

@Preview
@Composable
private fun ErrorMessagePreview() {
    ErrorMessage(
        onRetry = {},
    )
}

@Preview
@Composable
private fun MatchDetailsPreview() {
    MatchDetails(
        matchModel = getMatchModelMock(),
        players = Pair(
            listOf(
                getPlayerModelMock(),
                getPlayerModelMock(),
                getPlayerModelMock(),
            ),
            listOf(
                getPlayerModelMock(),
                getPlayerModelMock(),
                getPlayerModelMock(),
            ),
        ),
    )
}
