package com.manudev.data.character.remote.datasource

import com.manudev.data.character.remote.model.CharacterDto
import com.manudev.domain.APIResponseStatus
import com.manudev.data.response.DataWrapper

interface CharacterRemoteDataSource {

    suspend fun getAllCharacter(
        offset: Int,
        limit: Int,
    ): APIResponseStatus<DataWrapper<CharacterDto>>

    suspend fun getCharacterById(
        characterId: Int,
    ): APIResponseStatus<DataWrapper<CharacterDto>>

    suspend fun getCharacterByStartName(
        offset: Int,
        limit: Int,
        nameStartsWith: String,
    ): APIResponseStatus<DataWrapper<CharacterDto>>

}
