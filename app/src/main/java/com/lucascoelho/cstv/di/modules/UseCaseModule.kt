package com.lucascoelho.cstv.di.modules

import com.lucascoelho.domain.usecases.GetMatchesUseCaseImpl
import com.lucascoelho.domain.usecases.GetTeamDetailsUseCaseImpl
import com.lucascoelho.domain.usecases.IGetMatchesUseCase
import com.lucascoelho.domain.usecases.IGetTeamDetailsUseCase
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
