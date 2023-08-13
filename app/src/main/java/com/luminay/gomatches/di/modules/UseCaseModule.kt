package com.luminay.gomatches.di.modules

import com.example.domain.usecases.GetTeamPlayersUseCaseImpl
import com.example.domain.usecases.IGetTeamPlayersUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun providesMatchesUseCase(getMatchesUseCase: GetTeamPlayersUseCaseImpl): IGetTeamPlayersUseCase

    @Binds
    fun providesGetTeamPlayersUseCase(getTeamPlayersUseCase: GetTeamPlayersUseCaseImpl): IGetTeamPlayersUseCase
}
