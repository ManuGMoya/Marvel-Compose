package com.manudev.data.character.remote.datasource

import com.manudev.data.response.APIResponseStatus
import com.manudev.data.response.DataWrapper

interface CharacterRemoteDataSource {

    suspend fun getAllCharacter(
        offset: Int,
        limit: Int,
    ): APIResponseStatus<DataWrapper>

    suspend fun getCharacterById(
        characterId: Int,
    ): APIResponseStatus<DataWrapper>

    suspend fun getCharacterByStartName(
        offset: Int,
        limit: Int,
        nameStartsWith: String,
    ): APIResponseStatus<DataWrapper>

}
