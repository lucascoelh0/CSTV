package com.example.data.remote.repositories

import com.example.core.models.Resource
import com.example.data.remote.api.MatchesApi
import com.example.data.remote.models.toModel
import com.example.data.remote.utils.handleNetworkResponse
import com.example.domain.models.MatchModel
import com.example.domain.repositories.IMatchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MatchesRepositoryImpl @Inject constructor(
    private val matchesApi: MatchesApi,
) : IMatchesRepository {

    override fun getAllMatches(): Flow<Resource<List<MatchModel>>> {
        return flow {
            val response = matchesApi.getAllMatches()
            val resource = handleNetworkResponse(response) {
                it.toModel()
            }
            emit(resource)
        }
    }
}
