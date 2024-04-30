package com.manudev.data.character

import com.manudev.data.character.remote.APIResponseStatus
import com.manudev.data.character.remote.CharacterRemoteDataSource
import com.manudev.domain.model.CharacterDomain
import com.manudev.domain.repository.ICharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val remote: CharacterRemoteDataSource
) : ICharacterRepository {

    override fun getCharacters(offset: Int, limit: Int): Flow<List<CharacterDomain>> =
        flow {
            when (val response = remote.getAllCharacter(offset, limit)) {
                is APIResponseStatus.Success -> {
                    emit(response.data.data?.results?.map { it.toDomain() } ?: emptyList())
                }

                is APIResponseStatus.Error -> {
                    throw Exception(response.message)
                }
            }
        }

    override fun getCharacterById(characterId: Int): Flow<CharacterDomain> =
        flow {
            when (val response = remote.getCharacterById(characterId)) {
                is APIResponseStatus.Success -> {
                    emit(response.data.data?.results?.first()?.toDomain()!!)
                }

                is APIResponseStatus.Error -> {
                    throw Exception(response.message)
                }
            }

        }

    override fun getCharacterByName(
        offset: Int,
        limit: Int,
        nameStartsWith: String
    ): Flow<List<CharacterDomain>> =
        flow {

            when (val response = remote.getCharacterByStartName(
                offset = offset,
                limit = limit,
                nameStartsWith = nameStartsWith
            )) {
                is APIResponseStatus.Success -> {
                    emit(response.data.data?.results?.map { it.toDomain() } ?: emptyList())
                }

                is APIResponseStatus.Error -> {
                    throw Exception(response.message)
                }
            }
        }
}
