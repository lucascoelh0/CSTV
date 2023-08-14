package com.luminay.gomatches.di.modules

import com.example.domain.usecases.GetMatchesUseCaseImpl
import com.example.domain.usecases.GetTeamDetailsUseCaseImpl
import com.example.domain.usecases.IGetMatchesUseCase
import com.example.domain.usecases.IGetTeamDetailsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun providesMatchesUseCase(getMatchesUseCase: GetMatchesUseCaseImpl): IGetMatchesUseCase

    @Binds
    fun providesGetTeamPlayersUseCase(getTeamPlayersUseCase: GetTeamDetailsUseCaseImpl): IGetTeamDetailsUseCase
}
