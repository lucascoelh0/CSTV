package com.luminay.gomatches.features.matches.matchdetails

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.models.MatchModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun MatchDetailsScreen(
    id: Int,
    matchModel: MatchModel,
) {
    MatchDetails(
        matchModel = matchModel,
    )
}

@Composable
fun MatchDetails(
    matchModel: MatchModel,
    matchDetailsViewModel: MatchDetailsViewModel = hiltViewModel(),
) {
    matchDetailsViewModel.fetchTeamsDetails(matchModel)
}
