package com.lucascoelho.domain.repositories

import com.lucascoelho.core.models.Resource
import com.lucascoelho.domain.models.TeamDetailsModel
import kotlinx.coroutines.flow.Flow

fun interface ITeamsRepository {
    fun getTeamDetails(ids: String): Flow<Resource<List<TeamDetailsModel>>>
}
