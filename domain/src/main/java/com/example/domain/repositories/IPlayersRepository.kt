package com.example.domain.repositories

import com.example.core.models.Resource
import com.example.domain.models.PlayerModel
import kotlinx.coroutines.flow.Flow

fun interface IPlayersRepository {
    fun getTeamPlayers(id: Int): Flow<Resource<List<PlayerModel>>>
}
