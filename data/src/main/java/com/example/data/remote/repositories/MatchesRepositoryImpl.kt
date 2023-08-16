package com.example.data.remote.repositories

import com.example.core.models.Resource
import com.example.data.remote.api.MatchesApi
import com.example.data.remote.dtos.toModel
import com.example.data.remote.utils.handleNetworkResponse
import com.example.domain.models.MatchModel
import com.example.domain.repositories.IMatchesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MatchesRepositoryImpl @Inject constructor(
    private val matchesApi: MatchesApi,
) : IMatchesRepository {

    override fun getAllMatches(
        page: Int,
    ): Flow<Resource<List<MatchModel>>> {
        return flow {
            val response = matchesApi.getAllMatches(
                page = page,
            )
            val resource = handleNetworkResponse(response) {
                it.toModel()
            }
            emit(resource)
        }
    }
}
