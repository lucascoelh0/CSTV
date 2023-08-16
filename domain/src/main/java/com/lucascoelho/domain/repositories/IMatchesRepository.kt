package com.lucascoelho.domain.repositories

import com.lucascoelho.core.models.Resource
import com.lucascoelho.domain.models.MatchModel
import kotlinx.coroutines.flow.Flow

fun interface IMatchesRepository {

    fun getAllMatches(
        page: Int,
    ): Flow<Resource<List<MatchModel>>>
}
