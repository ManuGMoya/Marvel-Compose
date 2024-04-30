package com.manudev.data.character.remote

import com.manudev.data.character.remote.model.CharacterDataContainer
import com.manudev.data.character.remote.model.CharacterDataWrapper

interface CharacterRemoteDataSource {

    suspend fun getAllCharacter(
        offset: Int,
        limit: Int,
    ): APIResponseStatus<CharacterDataWrapper>

    suspend fun getCharacterById(
        characterId: Int,
    ): APIResponseStatus<CharacterDataWrapper>

    suspend fun getCharacterByStartName(
        offset: Int,
        limit: Int,
        nameStartsWith: String,
    ): APIResponseStatus<CharacterDataWrapper>

}
