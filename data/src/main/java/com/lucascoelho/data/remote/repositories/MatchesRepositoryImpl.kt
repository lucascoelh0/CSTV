package com.lucascoelho.data.remote.repositories

import com.lucascoelho.core.models.Resource
import com.lucascoelho.data.remote.api.MatchesApi
import com.lucascoelho.data.remote.dtos.toModel
import com.lucascoelho.data.remote.utils.handleNetworkResponse
import com.lucascoelho.domain.models.MatchModel
import com.lucascoelho.domain.repositories.IMatchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

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
