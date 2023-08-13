package com.example.domain.usecases

import com.example.core.models.Resource
import com.example.domain.models.PlayerModel
import kotlinx.coroutines.flow.Flow

fun interface IGetTeamPlayersUseCase {
    fun invoke(id: Int): Flow<Resource<List<PlayerModel>>>
}
