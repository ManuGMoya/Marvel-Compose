package com.manudev.data.character

import com.manudev.data.character.remote.CharacterApi
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
    ): Flow<Result<List<CharacterDomain>>> = flow {
        val response = service.getAllCharacters(offset, limit)
        if (response.isSuccessful) {
            val characters = response.body()?.data?.results?.map { it.toDomain() }
            if (characters != null) {
                emit(Result.success(characters))
            } else {
                emit(Result.failure(Exception("No characters found")))
            }
        } else {
            emit(Result.failure(Exception(response.message())))
        }
    }


    override suspend fun getCharacterById(characterId: Int): Flow<Result<CharacterDomain>> =
        flow {
            val response = service.getCharacterById(characterId)
            if (response.isSuccessful) {
                val character = response.body()?.data?.results?.first()?.toDomain()
                if (character != null) {
                    emit(Result.success(character))
                } else {
                    emit(Result.failure(Exception("No character found")))
                }
            } else {
                emit(Result.failure(Exception(response.message())))
            }
        }


    override suspend fun getCharacterByName(
        offset: Int,
        limit: Int,
        nameStartsWith: String
    ): Flow<Result<List<CharacterDomain>>> =
        flow {
            val response =
                service.getCharacterByStartName(offset, limit, nameStartsWith)

            if (response.isSuccessful) {
                val characters = response.body()?.data?.results?.map { it.toDomain() }
                if (characters != null) {
                    emit(Result.success(characters))
                } else {
                    emit(Result.failure(Exception("No characters found")))
                }
            } else {
                emit(Result.failure(Exception(response.message())))
            }
        }
}
