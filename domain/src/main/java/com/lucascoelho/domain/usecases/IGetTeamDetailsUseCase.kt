package com.lucascoelho.domain.usecases

import com.lucascoelho.core.models.Resource
import com.lucascoelho.domain.models.TeamDetailsModel
import kotlinx.coroutines.flow.Flow

fun interface IGetTeamDetailsUseCase {
    operator fun invoke(id: List<Int>): Flow<Resource<List<TeamDetailsModel>>>
}
