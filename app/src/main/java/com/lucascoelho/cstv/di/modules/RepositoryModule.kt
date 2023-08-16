package com.lucascoelho.cstv.di.modules

import com.lucascoelho.data.remote.repositories.MatchesRepositoryImpl
import com.lucascoelho.data.remote.repositories.TeamsRepositoryImpl
import com.lucascoelho.domain.repositories.IMatchesRepository
import com.lucascoelho.domain.repositories.ITeamsRepository
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
