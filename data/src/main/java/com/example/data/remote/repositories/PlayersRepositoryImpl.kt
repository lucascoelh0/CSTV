package com.example.data.remote.repositories

import com.example.core.models.Resource
import com.example.data.remote.api.TeamsApi
import com.example.data.remote.models.toModel
import com.example.data.remote.utils.handleNetworkResponse
import com.example.domain.models.PlayerModel
import com.example.domain.repositories.IPlayersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlayersRepositoryImpl @Inject constructor(
    private val teamsApi: TeamsApi,
) : IPlayersRepository {

    override fun getTeamPlayers(id: Int): Flow<Resource<List<PlayerModel>>> {
        return flow {
            val response = teamsApi.getTeamDetails(id)
            val resource = handleNetworkResponse(response) {
                it.toModel().first().players
            }
            emit(resource)
        }
    }
}
