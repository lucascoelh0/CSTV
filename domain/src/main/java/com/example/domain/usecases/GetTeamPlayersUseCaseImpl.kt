package com.example.domain.usecases

import com.example.core.models.Resource
import com.example.domain.models.PlayerModel
import com.example.domain.repositories.IPlayersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTeamPlayersUseCaseImpl @Inject constructor(
    private val playersRepository: IPlayersRepository,
) : IGetTeamPlayersUseCase {
    override fun invoke(id: Int): Flow<Resource<List<PlayerModel>>> {
        return playersRepository.getTeamPlayers(id)
    }
}
