package com.example.domain.usecases

import com.example.core.models.Resource
import com.example.domain.models.MatchModel
import kotlinx.coroutines.flow.Flow

fun interface IGetMatchesUseCase {
    operator fun invoke(): Flow<Resource<List<MatchModel>>>
}
