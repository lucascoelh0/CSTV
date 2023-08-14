package com.luminay.gomatches.di.modules

import com.example.data.remote.repositories.MatchesRepositoryImpl
import com.example.data.remote.repositories.TeamsRepositoryImpl
import com.example.domain.repositories.IMatchesRepository
import com.example.domain.repositories.ITeamsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun providesTeamsRepository(playerRepositoryImpl: TeamsRepositoryImpl): ITeamsRepository

    @Binds
    fun providesMatchRepository(matchRepositoryImpl: MatchesRepositoryImpl): IMatchesRepository
}
