package com.manudev.data.di


import com.manudev.data.character.CharacterRepositoryImpl
import com.manudev.data.character.remote.CharacterRemoteDataSource
import com.manudev.data.character.remote.CharacterRemoteDataSourceImpl
import com.manudev.domain.repository.ICharacterRepository
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
    fun bindsRepository(characterRepositoryImpl: CharacterRepositoryImpl): ICharacterRepository
    
    @Binds
    fun bindsCharacterRemoteDatasource(characterRemoteDataSourceImpl: CharacterRemoteDataSourceImpl): CharacterRemoteDataSource

}
