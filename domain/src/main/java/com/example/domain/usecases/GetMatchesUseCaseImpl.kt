package com.example.domain.usecases

import com.example.core.models.Resource
import com.example.domain.models.MatchModel
import com.example.domain.repositories.IMatchesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMatchesUseCaseImpl @Inject constructor(
    private val matchesRepository: IMatchesRepository,
) : IGetMatchesUseCase {
    override fun invoke(
        page: Int,
    ): Flow<Resource<List<MatchModel>>> {
        return matchesRepository.getAllMatches(page)
    }
}
