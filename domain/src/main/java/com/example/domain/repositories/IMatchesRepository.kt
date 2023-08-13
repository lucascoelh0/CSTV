package com.example.domain.repositories

import com.example.core.models.Resource
import com.example.domain.models.MatchModel
import kotlinx.coroutines.flow.Flow

fun interface IMatchesRepository {

    fun getAllMatches(): Flow<Resource<List<MatchModel>>>
}
