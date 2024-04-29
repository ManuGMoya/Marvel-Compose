package com.manudev.data.di


import com.manudev.data.character.remote.CharacterRemoteDataSource
import com.manudev.data.character.remote.CharacterRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {
    
    @Binds
    fun bindsCharacterRemoteDatasource(characterRemoteDataSourceImpl: CharacterRemoteDataSourceImpl): CharacterRemoteDataSource

}
