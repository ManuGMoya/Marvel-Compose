package com.manudev.data.character.remote

import com.manudev.data.character.remote.model.CharacterDataContainer

interface CharacterRemoteDataSource {

    suspend fun getAllCharacter(
        offset: Int,
        limit: Int,
    ): CharacterDataContainer

    suspend fun getCharacterById(
        characterId: Int,
    ): CharacterDataContainer

    suspend fun getCharacterByStartName(
        offset: Int,
        limit: Int,
        nameStartsWith: String,
    ): CharacterDataContainer

}
