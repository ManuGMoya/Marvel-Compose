package com.manudev.data.character

import com.manudev.data.character.remote.CharacterApi
import com.manudev.data.utils.Network.safeCall
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
            when (val response = service.getAllCharacters(offset, limit).safeCall()) {
                is APIResponseStatus.Success -> {
                    val characters = response.data.data?.results!!.map { it.toDomain() }
                    emit(APIResponseStatus.Success(characters))
                }

                is APIResponseStatus.Error -> {
                    emit(APIResponseStatus.Error(response.message))
                }
            }
        }

    override suspend fun getCharacterById(characterId: Int): Flow<APIResponseStatus<CharacterDomain>> =
        flow {
            when (val response = service.getCharacterById(characterId).safeCall()) {
                is APIResponseStatus.Success -> {
                    val character = response.data.data?.results!!.first().toDomain()
                    emit(APIResponseStatus.Success(character))
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
            when (val response =
                service.getCharacterByStartName(offset, limit, nameStartsWith).safeCall()) {
                is APIResponseStatus.Success -> {
                    val characters = response.data.data?.results!!.map { it.toDomain() }
                    emit(APIResponseStatus.Success(characters))
                }

                is APIResponseStatus.Error -> {
                    emit(APIResponseStatus.Error(response.message))
                }
            }
        }
}
