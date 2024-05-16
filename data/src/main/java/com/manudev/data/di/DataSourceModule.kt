package com.manudev.data.di


import com.manudev.data.character.CharacterRepositoryImpl
import com.manudev.data.comic.ComicRepositoryImpl
import com.manudev.domain.repository.ICharacterRepository
import com.manudev.domain.repository.IComicRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Singleton
    @Binds
    fun bindsCharacterRepository(characterRepositoryImpl: CharacterRepositoryImpl): ICharacterRepository


    @Singleton
    @Binds
    fun bindsComicRepository(comicRepositoryImpl: ComicRepositoryImpl): IComicRepository

}
