package com.example.domain.usecases

import com.example.core.models.Resource
import com.example.domain.models.TeamDetailsModel
import com.example.domain.repositories.ITeamsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTeamDetailsUseCaseImpl @Inject constructor(
    private val teamsRepository: ITeamsRepository,
) : IGetTeamDetailsUseCase {
    override operator fun invoke(id: List<Int>): Flow<Resource<List<TeamDetailsModel>>> {
        return teamsRepository.getTeamDetails(id.joinToString())
    }
}
