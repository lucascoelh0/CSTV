package com.lucascoelho.domain.usecases

import com.lucascoelho.core.models.Resource
import com.lucascoelho.domain.models.MatchModel
import kotlinx.coroutines.flow.Flow

fun interface IGetMatchesUseCase {
    operator fun invoke(
        page: Int,
    ): Flow<Resource<List<MatchModel>>>
}
