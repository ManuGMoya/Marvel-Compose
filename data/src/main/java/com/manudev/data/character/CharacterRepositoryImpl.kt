package com.manudev.data.character

import com.manudev.data.character.remote.CharacterApi
import com.manudev.domain.APIResponseStatus
import com.manudev.domain.model.CharacterDomain
import com.manudev.domain.repository.ICharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val service: CharacterApi
) : ICharacterRepository {

    override suspend fun getCharacters(
        offset: Int,
        limit: Int
    ): Flow<APIResponseStatus<List<CharacterDomain>>> =
        flow {
            try {
                val response = service.getAllCharacters(offset, limit)
                if (response.isSuccessful) {
                    val characters =
                        response.body()?.data?.results?.map { it.toDomain() } ?: emptyList()
                    emit(APIResponseStatus.Success(characters))
                } else {
                    emit(APIResponseStatus.Error("Error: ${response.code()}"))
                }
            } catch (e: Exception) {
                emit(APIResponseStatus.Error(e.message ?: "Unknown error"))
            }
        }

    override suspend fun getCharacterById(characterId: Int): Flow<APIResponseStatus<CharacterDomain>> =
        flow {
            try {
                val response = service.getCharacterById(characterId)
                if (response.isSuccessful) {
                    val character = response.body()?.data?.results?.first()?.toDomain()
                    if (character != null) {
                        emit(APIResponseStatus.Success(character))
                    } else {
                        emit(APIResponseStatus.Error("Character not found"))
                    }
                } else {
                    emit(APIResponseStatus.Error("Error: ${response.code()}"))
                }
            } catch (e: Exception) {
                emit(APIResponseStatus.Error(e.message ?: "Unknown error"))
            }
        }

    override suspend fun getCharacterByName(
        offset: Int,
        limit: Int,
        nameStartsWith: String
    ): Flow<APIResponseStatus<List<CharacterDomain>>> =
        flow {
            try {
                val response = service.getCharacterByStartName(offset, limit, nameStartsWith)
                if (response.isSuccessful) {
                    val characters =
                        response.body()?.data?.results?.map { it.toDomain() } ?: emptyList()
                    emit(APIResponseStatus.Success(characters))
                } else {
                    emit(APIResponseStatus.Error("Error: ${response.code()}"))
                }
            } catch (e: Exception) {
                emit(APIResponseStatus.Error(e.message ?: "Unknown error"))
            }
        }
}
