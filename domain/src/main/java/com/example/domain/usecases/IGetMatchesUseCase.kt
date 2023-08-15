package com.example.domain.usecases

import com.example.core.models.Resource
import com.example.domain.models.MatchModel
import kotlinx.coroutines.flow.Flow

fun interface IGetMatchesUseCase {
    operator fun invoke(
        page: Int,
    ): Flow<Resource<List<MatchModel>>>
}
