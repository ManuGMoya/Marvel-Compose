package com.manudev.data.di

import com.manudev.data.character.remote.CharacterApi
import com.manudev.data.network.Interceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideOkHttpClient(requestInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideCharacterMarvelApi(retrofit: Retrofit): CharacterApi = retrofit.create()

}