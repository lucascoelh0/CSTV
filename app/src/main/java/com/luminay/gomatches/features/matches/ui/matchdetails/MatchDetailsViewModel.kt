package com.luminay.gomatches.features.matches.ui.matchdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.models.Resource
import com.example.domain.models.MatchModel
import com.example.domain.models.PlayerModel
import com.example.domain.models.TeamDetailsModel
import com.example.domain.usecases.IGetTeamDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchDetailsViewModel @Inject constructor(
    private val getTeamDetailsUseCase: IGetTeamDetailsUseCase,
) : ViewModel() {

    private val _teams = MutableStateFlow<Resource<List<TeamDetailsModel>>>(Resource.loading(null))
    val teams: Flow<Resource<List<TeamDetailsModel>>> = _teams.asStateFlow()

    fun fetchTeamsDetails(
        matchModel: MatchModel,
    ) = viewModelScope.launch {
        flow {
            emit(Resource.loading(null))
            emitAll(getTeamDetailsUseCase(getTeamsIds(matchModel)))
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
        matchModel: MatchModel,
        teams: List<TeamDetailsModel>,
    ): Pair<List<PlayerModel>, List<PlayerModel>> {
        val sortedTeams = teams.sortedBy {
            val teamIndex = matchModel.opponents.indexOfFirst { opponent ->
                opponent.opponent.name == it.name
            }
            teamIndex
        }

        return Pair(
            sortedTeams.first().players,
            if (sortedTeams.size > 1) sortedTeams.last().players else emptyList(),
        )
    }
}
