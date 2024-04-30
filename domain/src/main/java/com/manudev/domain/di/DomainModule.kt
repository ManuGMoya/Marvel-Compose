package com.manudev.domain.di

import com.manudev.domain.usecases.character.CharacterUseCase
import com.manudev.domain.usecases.character.CharacterUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    fun bindCharacterUseCase(characterUseCaseImpl: CharacterUseCaseImpl): CharacterUseCase
}
