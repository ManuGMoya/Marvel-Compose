package com.manudev.data.di

import com.manudev.data.BuildConfig
import com.manudev.data.network.PrivateKey
import com.manudev.data.network.PublicKey

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ApiKeyModule {
    @Provides
    @PrivateKey
    fun providePrivateKey(): String = BuildConfig.MARVEL_PRIVATE_KEY

    @Provides
    @PublicKey
    fun providePublicKey(): String = BuildConfig.MARVEL_PUBLIC_KEY
}
