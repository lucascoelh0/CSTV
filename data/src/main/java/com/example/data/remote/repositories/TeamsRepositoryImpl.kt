package com.example.data.remote.repositories

import com.example.core.models.Resource
import com.example.data.remote.api.TeamsApi
import com.example.data.remote.dtos.toModel
import com.example.data.remote.utils.handleNetworkResponse
import com.example.domain.models.TeamDetailsModel
import com.example.domain.repositories.ITeamsRepository
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
