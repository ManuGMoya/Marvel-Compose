package com.manudev.data.character.remote

import com.manudev.data.character.remote.model.CharacterDataWrapper
import javax.inject.Inject

class CharacterRemoteDataSourceImpl @Inject constructor(
    private val service: CharacterApi
) : CharacterRemoteDataSource {
    override suspend fun getAllCharacter(
        offset: Int,
        limit: Int
    ): APIResponseStatus<CharacterDataWrapper> {
        return try {
            val response = service.getAllCharacters(offset, limit)
            if (response.isSuccessful) {
                APIResponseStatus.Success(response.body()!!)
            } else {
                APIResponseStatus.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            APIResponseStatus.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getCharacterById(characterId: Int): APIResponseStatus<CharacterDataWrapper> {
        return try {
            val response = service.getCharacterById(characterId)
            if (response.isSuccessful) {
                APIResponseStatus.Success(response.body()!!)
            } else {
                APIResponseStatus.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            APIResponseStatus.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getCharacterByStartName(
        offset: Int,
        limit: Int,
        nameStartsWith: String
    ): APIResponseStatus<CharacterDataWrapper> {
        return try {
            val response = service.getCharacterByStartName(
                offset = offset,
                limit = limit,
                nameStartsWith = nameStartsWith
            )
            if (response.isSuccessful) {
                APIResponseStatus.Success(response.body()!!)
            } else {
                APIResponseStatus.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            APIResponseStatus.Error(e.message ?: "Unknown error")
        }
    }

}