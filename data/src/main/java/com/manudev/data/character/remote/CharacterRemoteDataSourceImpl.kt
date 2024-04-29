package com.manudev.data.character.remote

import com.manudev.data.character.remote.model.CharacterDataContainer
import javax.inject.Inject

class CharacterRemoteDataSourceImpl @Inject constructor(
    private val service: CharacterApi
) : CharacterRemoteDataSource {
    override suspend fun getAllCharacter(offset: Int, limit: Int): CharacterDataContainer =
        service.getAllCharacters(offset,limit)

    override suspend fun getCharacterById(characterId: Int): CharacterDataContainer =
        service.getCharacterById(characterId)

    override suspend fun getCharacterByStartName(
        offset: Int,
        limit: Int,
        nameStartsWith: String
    ): CharacterDataContainer =
        service.getCharacterByStartName(
            offset = offset,
            limit = limit,
            nameStartsWith = nameStartsWith
        )

}