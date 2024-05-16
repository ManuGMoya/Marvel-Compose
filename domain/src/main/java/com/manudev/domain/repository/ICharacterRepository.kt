package com.manudev.domain.repository

import com.manudev.domain.model.CharacterDomain
import kotlinx.coroutines.flow.Flow

interface ICharacterRepository {

    suspend fun getCharacters(
        offset: Int,
        limit: Int
    ): Flow<Result<List<CharacterDomain>>>

    suspend fun getCharacterById(
        characterId: Int,
    ): Flow<Result<CharacterDomain>>

    suspend fun getCharacterByName(
        offset: Int,
        limit: Int,
        nameStartsWith: String
    ): Flow<Result<List<CharacterDomain>>>
}
