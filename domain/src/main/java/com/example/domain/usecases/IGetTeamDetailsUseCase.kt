package com.example.domain.usecases

import com.example.core.models.Resource
import com.example.domain.models.TeamDetailsModel
import kotlinx.coroutines.flow.Flow

fun interface IGetTeamDetailsUseCase {
    operator fun invoke(id: List<Int>): Flow<Resource<List<TeamDetailsModel>>>
}
