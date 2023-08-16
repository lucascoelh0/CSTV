package com.lucascoelho.domain.usecases

import com.lucascoelho.core.models.Resource
import com.lucascoelho.domain.models.MatchModel
import com.lucascoelho.domain.repositories.IMatchesRepository
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
