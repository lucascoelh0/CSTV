package com.example.domain.repositories

import com.example.core.models.Resource
import com.example.domain.models.TeamDetailsModel
import kotlinx.coroutines.flow.Flow

fun interface ITeamsRepository {
    fun getTeamDetails(ids: String): Flow<Resource<List<TeamDetailsModel>>>
}
