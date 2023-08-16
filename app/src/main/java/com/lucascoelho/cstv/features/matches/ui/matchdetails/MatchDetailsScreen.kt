package com.lucascoelho.cstv.features.matches.ui.matchdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lucascoelho.core.models.Status
import com.lucascoelho.core.utils.TimeUtils
import com.lucascoelho.cstv.R
import com.lucascoelho.cstv.common.ui.messages.ErrorMessage
import com.lucascoelho.cstv.features.matches.ui.common.TeamVsTeam
import com.lucascoelho.cstv.features.matches.ui.matchdetails.players.PlayersTable
import com.lucascoelho.cstv.theme.DarkBlue900
import com.lucascoelho.cstv.utils.getMatchModelMock
import com.lucascoelho.cstv.utils.getPlayerModelMock
import com.lucascoelho.domain.models.MatchModel
import com.lucascoelho.domain.models.MatchStatus
import com.lucascoelho.domain.models.PlayerModel
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
                ),
        )

        MatchStatus(
            matchModel = matchModel,
        )
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
        },
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
                        players = matchDetailsViewModel.getTeamPlayersPair(
                            matchModel = matchModel,
                            teams = it,
                        ),
                    )
                } ?: run {
                    ErrorMessage(
                        message = stringResource(id = R.string.loading_match_details_error),
                        onRetry = {
                            matchDetailsViewModel.fetchTeamsDetails(matchModel)
                        },
                    )
                }
            }

            else -> {
                ErrorMessage(
                    message = stringResource(id = R.string.loading_match_details_error),
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
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        TeamVsTeam(
            matchModel = matchModel,
        )

        Text(
            text = if (matchModel.status == MatchStatus.RUNNING) {
                stringResource(id = R.string.now)
            } else {
                TimeUtils.formatBeginDate(matchModel.beginAt)
            },
            modifier = Modifier
                .padding(top = 20.dp),
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.W700,
        )

        PlayersTable(
            players = players,
        )
    }
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
