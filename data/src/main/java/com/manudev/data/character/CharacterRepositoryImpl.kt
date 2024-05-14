package com.manudev.data.character

import com.manudev.data.character.remote.datasource.CharacterRemoteDataSource
import com.manudev.domain.APIResponseStatus
import com.manudev.domain.model.CharacterDomain
import com.manudev.domain.repository.ICharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val remote: CharacterRemoteDataSource
) : ICharacterRepository {

    override suspend fun getCharacters(
        offset: Int,
        limit: Int
    ): Flow<APIResponseStatus<List<CharacterDomain>>> =
        flow {
            val response = remote.getAllCharacter(offset, limit)
            if (response is APIResponseStatus.Success) {
                emit(APIResponseStatus.Success(response.data.data?.results?.map {
                    it.toDomain()
                } ?: emptyList()))
            } else {
                emit(APIResponseStatus.Error((response as APIResponseStatus.Error).message))
            }
        }

    override suspend fun getCharacterById(characterId: Int): Flow<APIResponseStatus<CharacterDomain>> =
        flow {
            when (val response = remote.getCharacterById(characterId)) {
                is APIResponseStatus.Success -> {
                    val character = response.data.data?.results?.first()?.toDomain()
                    if (character != null) {
                        emit(APIResponseStatus.Success(character))
                    } else {
                        emit(APIResponseStatus.Error("Character not found"))
                    }
                }

                is APIResponseStatus.Error -> {
                    emit(APIResponseStatus.Error(response.message))
                }
            }
        }

    override suspend fun getCharacterByName(
        offset: Int,
        limit: Int,
        nameStartsWith: String
    ): Flow<APIResponseStatus<List<CharacterDomain>>> =
        flow {
            val response = remote.getCharacterByStartName(offset, limit, nameStartsWith)
            if (response is APIResponseStatus.Success) {
                emit(APIResponseStatus.Success(response.data.data?.results?.map {
                    it.toDomain()
                } ?: emptyList()))
            } else {
                emit(APIResponseStatus.Error((response as APIResponseStatus.Error).message))
            }
        }
}
