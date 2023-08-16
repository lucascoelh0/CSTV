package com.lucascoelho.data.remote.repositories

import com.lucascoelho.core.models.Resource
import com.lucascoelho.data.remote.api.TeamsApi
import com.lucascoelho.data.remote.dtos.toModel
import com.lucascoelho.data.remote.utils.handleNetworkResponse
import com.lucascoelho.domain.models.TeamDetailsModel
import com.lucascoelho.domain.repositories.ITeamsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TeamsRepositoryImpl @Inject constructor(
    private val teamsApi: TeamsApi,
) : ITeamsRepository {

    override fun getTeamDetails(ids: String): Flow<Resource<List<TeamDetailsModel>>> {
        return flow {
            val response = teamsApi.getTeamDetails(ids)
            val resource = handleNetworkResponse(response) {
                it.toModel()
            }
            emit(resource)
        }
    }
}
