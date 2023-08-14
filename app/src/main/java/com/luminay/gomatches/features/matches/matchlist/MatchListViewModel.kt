package com.luminay.gomatches.features.matches.matchlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.constants.EMPTY
import com.example.core.constants.TBD
import com.example.core.models.Resource
import com.example.domain.models.MatchModel
import com.example.domain.models.OpponentModel
import com.example.domain.models.TeamModel
import com.example.domain.usecases.IGetMatchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchListViewModel @Inject constructor(
    private val getMatchesUseCase: IGetMatchesUseCase,
) : ViewModel() {

    private val _matches = MutableStateFlow<Resource<List<MatchModel>>>(Resource.loading(null))
    val matches: Flow<Resource<List<MatchModel>>> = _matches.asStateFlow()

    init {
        fetchData()
    }

    fun fetchData() = viewModelScope.launch {
        flow {
            emit(Resource.loading(null))
            emitAll(getMatchesUseCase())
        }.catch {
            Log.e("MatchListViewModel", "fetchData: ", it)
        }.collect { result ->
            _matches.value = result
        }
    }

    fun getOpponentsInfo(
        opponents: List<OpponentModel>,
    ): Pair<TeamModel, TeamModel> =
        when (opponents.size) {
            0 -> {
                Pair(
                    getEmptyTeamModel(),
                    getEmptyTeamModel(),
                )
            }

            1 -> {
                Pair(
                    opponents.first().opponent,
                    getEmptyTeamModel(),
                )
            }

            else -> {
                Pair(
                    opponents.first().opponent,
                    opponents[1].opponent,
                )
            }
        }

    private fun getEmptyTeamModel(): TeamModel = TeamModel(
        id = -1,
        name = TBD,
        imageUrl = EMPTY,
        slug = EMPTY,
    )
}
