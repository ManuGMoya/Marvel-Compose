package com.manudev.data.di


import com.manudev.data.character.CharacterRepositoryImpl
import com.manudev.data.character.remote.datasource.CharacterRemoteDataSource
import com.manudev.data.character.remote.datasource.CharacterRemoteDataSourceImpl
import com.manudev.data.comic.ComicRepositoryImpl
import com.manudev.data.comic.remote.datasource.ComicRemoteDataSource
import com.manudev.data.comic.remote.datasource.ComicRemoteDataSourceImpl
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

    @Binds
    fun bindsCharacterRemoteDatasource(characterRemoteDataSourceImpl: CharacterRemoteDataSourceImpl): CharacterRemoteDataSource


    @Singleton
    @Binds
    fun bindsComicRepository(comicRepositoryImpl: ComicRepositoryImpl): IComicRepository

    @Binds
    fun bindsComicRemoteDatasource(comicRemoteDataSourceImpl: ComicRemoteDataSourceImpl): ComicRemoteDataSource

}
