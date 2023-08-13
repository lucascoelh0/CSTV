package com.luminay.gomatches.di.modules

import com.example.data.remote.repositories.MatchesRepositoryImpl
import com.example.data.remote.repositories.PlayersRepositoryImpl
import com.example.domain.repositories.IMatchesRepository
import com.example.domain.repositories.IPlayersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun providesPlayerRepository(playerRepositoryImpl: PlayersRepositoryImpl): IPlayersRepository

    @Binds
    fun providesMatchRepository(matchRepositoryImpl: MatchesRepositoryImpl): IMatchesRepository
}
