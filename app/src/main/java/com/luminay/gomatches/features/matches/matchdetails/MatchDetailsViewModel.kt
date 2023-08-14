package com.luminay.gomatches.features.matches.matchdetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.constants.EMPTY
import com.example.core.models.Resource
import com.example.domain.models.MatchModel
import com.example.domain.models.PlayerModel
import com.example.domain.models.TeamDetailsModel
import com.example.domain.usecases.GetTeamDetailsUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@HiltViewModel
class MatchDetailsViewModel @Inject constructor(
    private val getTeamPlayersUseCaseImpl: GetTeamDetailsUseCaseImpl,
) : ViewModel() {

    private val _teams = MutableStateFlow<Resource<List<TeamDetailsModel>>>(Resource.loading(null))
    val teams: Flow<Resource<List<TeamDetailsModel>>> = _teams.asStateFlow()

    fun fetchTeamsDetails(
        matchModel: MatchModel,
    ) = viewModelScope.launch {
        flow {
            emit(Resource.loading(null))
            emitAll(getTeamPlayersUseCaseImpl(getTeamsIds(matchModel)))
        }.catch {
            Log.e("MatchDetailsViewModel", "fetchTeamsDetails: ", it)
        }.collect { result ->
            _teams.value = result
        }
    }

    private fun getTeamsIds(
        matchModel: MatchModel,
    ): List<Int> {
        val teamIds = mutableListOf<Int>()
        matchModel.opponents.forEach {
            teamIds.add(it.opponent.id)
        }
        return teamIds
    }

    fun getTeamPlayersPair(
        teams: List<TeamDetailsModel>,
    ): Pair<List<PlayerModel>, List<PlayerModel>> {
        return when (teams.size) {
            0 -> {
                Pair(
                    emptyList(),
                    emptyList(),
                )
            }

            1 -> {
                Pair(
                    teams.first().players,
                    emptyList(),
                )
            }

            else -> {
                Pair(
                    teams.first().players,
                    teams.last().players,
                )
            }
        }
    }

//    private fun getEmptyTeamDetails(): TeamDetailsModel = TeamDetailsModel(
//        name = EMPTY,
//        players = emptyList(),
//        imageUrl = EMPTY,
//    )
}
