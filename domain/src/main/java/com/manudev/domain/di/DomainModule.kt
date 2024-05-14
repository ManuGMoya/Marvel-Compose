package com.manudev.domain.di

import com.manudev.domain.usecases.character.GetCharacterByIdUseCase
import com.manudev.domain.usecases.character.GetCharacterByIdUseCaseImpl
import com.manudev.domain.usecases.character.GetCharacterByNameUseCase
import com.manudev.domain.usecases.character.GetCharacterByNameUseCaseImpl
import com.manudev.domain.usecases.character.GetCharactersUseCase
import com.manudev.domain.usecases.character.GetCharactersUseCaseImpl
import com.manudev.domain.usecases.comic.ComicUseCase
import com.manudev.domain.usecases.comic.ComicUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    fun bindGetCharactersUseCase(getCharactersUseCaseImpl: GetCharactersUseCaseImpl): GetCharactersUseCase

    @Binds
    fun bindGetCharacterByIdUseCase(getCharacterByIdUseCaseImpl: GetCharacterByIdUseCaseImpl): GetCharacterByIdUseCase

    @Binds
    fun bindGetCharacterByNameUseCase(getCharacterByNameUseCaseImpl: GetCharacterByNameUseCaseImpl): GetCharacterByNameUseCase

    @Binds
    fun bindComicUseCase(comicUseCaseImpl: ComicUseCaseImpl): ComicUseCase
}
