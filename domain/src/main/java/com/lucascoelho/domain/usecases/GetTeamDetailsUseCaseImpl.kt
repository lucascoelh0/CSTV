package com.lucascoelho.domain.usecases

import com.lucascoelho.core.models.Resource
import com.lucascoelho.domain.models.TeamDetailsModel
import com.lucascoelho.domain.repositories.ITeamsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTeamDetailsUseCaseImpl @Inject constructor(
    private val teamsRepository: ITeamsRepository,
) : IGetTeamDetailsUseCase {
    override operator fun invoke(id: List<Int>): Flow<Resource<List<TeamDetailsModel>>> {
        return teamsRepository.getTeamDetails(id.joinToString())
    }
}
